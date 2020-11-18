package clone;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.clone.CycloneClone;
import com.csi.czech.source.NiCadSource;
import com.csi.czech.source.CycloneSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NiCadCloneTest {
    @Nested
    public class EqualsTest {
        @Test
        public void testNestedMultiClass() {
            Clone clone1 = new NiCadClone(22L, 2L);
            clone1.addSource(new NiCadSource("file.txt", 5L, 25L, 2L));
            clone1.addSource(new NiCadSource("file2.txt", 6L, 21L, 2L));

            Clone clone2 = new CycloneClone("module", 2L);
            clone2.addSource(new CycloneSource("file.txt", 15L, 17L, 2.0));
            clone2.addSource(new CycloneSource("file2.txt", 17L, 18L, 2.0));

            assertEquals(clone1, clone2);
        }
    }
}
