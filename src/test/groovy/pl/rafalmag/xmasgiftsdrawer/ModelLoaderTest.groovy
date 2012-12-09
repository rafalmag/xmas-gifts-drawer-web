package pl.rafalmag.xmasgiftsdrawer

import com.google.common.io.Closeables
import com.google.common.io.Resources
import org.junit.Test

import static org.fest.assertions.api.Assertions.assertThat

public class ModelLoaderTest {

    @Test
    public void should_load_model() throws Exception {
        // given
        def streamToModel = Resources.getResource(ModelLoaderTest.class, "/model.csv").openStream()
        try {
            // when
            def modelLoader = new ModelLoader(streamToModel)
            Model model = modelLoader.load()
            def persons = model.getPersons()
            // then
            assertThat(model).isNotNull()
            assertThat(persons).contains(
                    new Person("A"),
                    new Person("B"),
                    new Person("C"),
                    new Person("D")
            )
        } finally {
            Closeables.closeQuietly(streamToModel)
        }
    }
}

