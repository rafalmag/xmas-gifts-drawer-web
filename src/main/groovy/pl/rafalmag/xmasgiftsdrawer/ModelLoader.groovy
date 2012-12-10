package pl.rafalmag.xmasgiftsdrawer

import com.google.common.base.Splitter
import com.google.common.collect.Lists

class ModelLoader {

    private final InputStreamReader reader

    ModelLoader(InputStream inputStream) {
        this.reader = new InputStreamReader(inputStream)
    }

    Model load() {
        List<Person> persons = parseHeader()
        def model = new Model([])
        parseValues(model, persons)
        model
    }

    def List<Person> parseHeader() {
        def header = readLine()
        def names = Splitter.on(';').omitEmptyStrings().split(header)
        names.collect { name -> new Person(name) }
    }

    private String readLine() {
        String line = ""
        while (line?.isEmpty()) {
            line = reader.readLine()
        }
        line
    }

    def parseValues(Model model, List<Person> persons) {
        persons.each { giver ->
            String line = readLine()
            parseLine(model,giver,persons,line)
        }
        assert readLine() == null
    }

    def static void parseLine(Model model, Person giver,List<Person> getters, String line) {
        def values = Lists.newLinkedList(Splitter.on(';').split(line))
        values.removeFirst();

        getters.each { getter ->
            def value = values.removeFirst()
            switch(value){
                case '1':
                    model.setCanGive(giver, getter)
                    break;
                case '0':
                    model.setCannotGive(giver, getter)
                    break;
                default:
                    //TODO my exception
                    throw new IOException("Value: "+value + " in line: "+line+" not supported.")
            }
        }
    }
}

