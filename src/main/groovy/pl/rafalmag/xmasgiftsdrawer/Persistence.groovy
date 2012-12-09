package pl.rafalmag.xmasgiftsdrawer

import com.google.common.base.Function
import com.google.common.base.Splitter
import com.google.common.collect.Collections2

class Persistence {

	public Persistence(InputStream inputStream){

	}

    Model load(InputStream inputStream) {
        def reader = new InputStreamReader(inputStream);

        def header = reader.readLine()
        def names = Splitter.on(';').omitEmptyStrings().split(header)
        def persons = names.collect {name -> new Person(name)}
        new Model(persons)
    }
}
