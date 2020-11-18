package clone;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.MossClone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.clone.CycloneClone;
import com.csi.czech.source.MossSource;
import com.csi.czech.source.NiCadSource;
import com.csi.czech.source.CycloneSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CloneTest {
    @Nested
    public class EqualsTest {
        @Nested
        public class EqualsExactDifferentClassTest {
            private Clone mossClone;
            private Clone cycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.44));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.78));

                cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("file.txt", 15L, 20L, 0.2));
                cycloneClone.addSource(new CycloneSource("file2.txt", 33L, 45L, 0.1));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 45L, 1L));
            }

            @Test
            public void testMossNicad() {
                assertEquals(mossClone, nicadClone);
            }

            @Test
            public void testMossCyclone() {
                assertEquals(mossClone, cycloneClone);
            }

            @Test
            public void testNicadCyclone() {
                assertEquals(nicadClone, cycloneClone);
            }
        }

        @Nested
        public class NestedTest {
            private Clone mossOuterClone;
            private Clone mossInnerClone;
            private Clone cycloneOuterClone;
            private Clone cycloneInnerClone;
            private Clone nicadOuterClone;
            private Clone nicadInnerClone;

            @BeforeEach
            public void init() {
                mossOuterClone = new MossClone();
                mossOuterClone.addSource(new MossSource("b.b", 200L, 300L,
                        0.22));
                mossOuterClone.addSource(new MossSource("a.a", 100L, 200L,
                        0.79));

                mossInnerClone = new MossClone();
                mossInnerClone.addSource(new MossSource("a.a", 150L, 155L,
                        0.98));
                mossInnerClone.addSource(new MossSource("b.b", 250L, 255L,
                        0.99));

                cycloneOuterClone = new CycloneClone("file", 23L);
                cycloneOuterClone.addSource(new CycloneSource("b.b", 200L,
                        300L, 0.2));
                cycloneOuterClone.addSource(new CycloneSource("a.a", 100L,
                        200L, 0.2));

                cycloneInnerClone = new CycloneClone("file", 2L);
                cycloneInnerClone.addSource(new CycloneSource("a.a", 155L,
                        160L, 2.1));
                cycloneInnerClone.addSource(new CycloneSource("b.b", 255L,
                        260L, 2.3));

                nicadOuterClone = new NiCadClone(23L, 2L);
                nicadOuterClone.addSource(new NiCadSource("b.b", 200L, 300L,
                        41L));
                nicadOuterClone.addSource(new NiCadSource("a.a", 100L, 200L,
                        34L));

                nicadInnerClone = new NiCadClone(442L, 35L);
                nicadInnerClone.addSource(new NiCadSource("a.a", 160L, 165L,
                        2L));
                nicadInnerClone.addSource(new NiCadSource("b.b", 265L, 270L,
                        53L));
            }

            @Test
            public void testMossOuterNicadInner() {
                assertEquals(mossOuterClone, nicadInnerClone);
            }

            @Test
            public void testMossOuterCycloneInner() {
                assertEquals(mossOuterClone, cycloneInnerClone);
            }

            @Test
            public void testCycloneOuterMossInner() {
                assertEquals(cycloneOuterClone, mossInnerClone);
            }

            @Test
            public void testCycloneOuterNicadInner() {
                assertEquals(cycloneOuterClone, nicadInnerClone);
            }

            @Test
            public void testNicadOuterMossInner() {
                assertEquals(nicadOuterClone, mossInnerClone);
            }

            @Test
            public void testNicadOuterCycloneInner() {
                assertEquals(nicadOuterClone, cycloneInnerClone);
            }
        }

        @Nested
        public class WithinErrorTest {
            private Clone mossClone;
            private Clone cycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.42));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.99));

                cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("file.txt", 12L, 17L
                        , 0.2));
                cycloneClone.addSource(new CycloneSource("file2.txt", 30L,
                        42L, 1.0));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 18L, 23L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 36L, 48L,
                        1L));
            }

            @Test
            public void testAdjustedBefore() {
                assertEquals(mossClone, cycloneClone);
            }

            @Test
            public void testAdjustedAfter() {
                assertEquals(mossClone, nicadClone);
            }

            @Test
            public void testNotWithinRange() {
                assertNotEquals(nicadClone, cycloneClone);
            }
        }

        @Nested
        public class NotEqualsTest {
            private Clone mossClone;
            private Clone cycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.89));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.87));

                cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("file.txt", 15L,
                        20L, 2.0));
                cycloneClone.addSource(new CycloneSource("file2.txt", 33L, 45L, 1.0));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 45L, 1L));
            }

            @Test
            public void testNotSameFirstFileName() {
                cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("filea.txt", 15L,
                        20L, 2.0));
                cycloneClone.addSource(new CycloneSource("file2.txt", 33L, 45L, 1.0));
                assertNotEquals(mossClone, cycloneClone);
            }

            @Test
            public void testNotSameSecondFileName() {
                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("test.txt", 33L, 45L, 1L));
                assertNotEquals(mossClone, nicadClone);
            }

            @Test
            public void testNotSameFirstStartLine() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 20L, 20L, 0.95));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.96));
                assertNotEquals(nicadClone, mossClone);
            }

            @Test
            public void testNotSameSecondStartLine() {
                cycloneClone = new CycloneClone("module", 2L);
                cycloneClone.addSource(new CycloneSource("file.txt", 15L,
                        20L, 2.0));
                cycloneClone.addSource(new CycloneSource("file2.txt", 100L, 45L
                        , 1.0));
                assertNotEquals(mossClone, cycloneClone);
            }

            @Test
            public void testNotSameFirstEndLine() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 33L,
                        0.97));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.99));
                assertNotEquals(cycloneClone, mossClone);
            }

            @Test
            public void testNotSameSecondEndLine() {
                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 33L,
                        1L));
                assertNotEquals(cycloneClone, nicadClone);
            }
        }
    }

    @Test
    public void multiTest() {
        Clone cycloneClone1 = new CycloneClone("module", 2L);
        cycloneClone1.addSource(new CycloneSource("filea.txt", 15L,
                20L, 2.0));
        cycloneClone1.addSource(new CycloneSource("file2.txt", 35L, 45L, 1.0));

        Clone cycloneClone2 = new CycloneClone("module", 2L);
        cycloneClone2.addSource(new CycloneSource("filea.txt", 10L,
                25L, 2.0));
        cycloneClone2.addSource(new CycloneSource("file2.txt", 25L, 55L, 1.0));

        assertEquals(cycloneClone1, cycloneClone2);
    }
}
