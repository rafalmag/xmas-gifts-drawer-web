package pl.rafalmag.xmasgiftsdrawer

import com.google.common.base.Function
import com.google.common.base.Splitter
import com.google.common.collect.Collections2

class ModelLoader {

    private final InputStreamReader reader

    ModelLoader(InputStream inputStream){
        this.reader = new InputStreamReader(inputStream)
    }

    Model load() {
        List<Person> persons = parseHeader()
        new Model(persons)
    }

    private List<Person> parseHeader() {
        def header = reader.readLine()
        def names = Splitter.on(';').omitEmptyStrings().split(header)
        names.collect { name -> new Person(name) }
    }
}

