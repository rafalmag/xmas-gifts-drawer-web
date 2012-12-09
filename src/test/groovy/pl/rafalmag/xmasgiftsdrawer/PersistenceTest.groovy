package pl.rafalmag.xmasgiftsdrawer

import com.google.common.io.Closeables
import com.google.common.io.Resources
import org.junit.Test

import static org.fest.assertions.api.Assertions.assertThat

public class PersistenceTest {

    @Test
    public void should_load_model() throws Exception {
        // given
        def streamToModel = Resources.getResource(PersistenceTest.class, "/model.csv").openStream()
        try {
            // when
            def persistence = new Persistence()
            Model model = persistence.load(streamToModel)
            // then
            assertThat(model).isNotNull();
        } finally {
            Closeables.closeQuietly(streamToModel)
        }
    }
}

