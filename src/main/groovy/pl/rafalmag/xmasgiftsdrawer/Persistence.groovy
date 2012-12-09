package pl.rafalmag.xmasgiftsdrawer
class Persistence {

	public Persistence(InputStream inputStream){

	}

    Model load(InputStream inputStream) {
        def reader = new InputStreamReader(inputStream);
        def header = reader.readLine()
//        Splitter.
        new Model([])
    }
}
