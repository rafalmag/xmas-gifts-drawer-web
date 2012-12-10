package pl.rafalmag.xmasgiftsdrawer

import com.google.common.base.Splitter
import com.google.common.base.Strings
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
        while (line != null && line.isEmpty()) {
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

    def void parseLine(Model model, Person giver,List<Person> getters, String line) {
        while (line.isEmpty()) {
            line = reader.readLine()
        }
        def values = Lists.newLinkedList(Splitter.on(';').split(line))
        values.removeFirst();

        getters.each { getter ->
            def value = values.removeFirst()
            if (value == '1')
                model.setCanGive(giver, getter)
            else
                model.setCannotGive(giver, getter)
        }
    }
}

