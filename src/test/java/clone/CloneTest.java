package clone;

import com.csi.czech.clone.Clone;
import com.csi.czech.clone.MossClone;
import com.csi.czech.clone.NiCadClone;
import com.csi.czech.clone.PyCloneClone;
import com.csi.czech.source.MossSource;
import com.csi.czech.source.NiCadSource;
import com.csi.czech.source.PyCloneSource;
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
            private Clone pycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.44));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.78));

                pycloneClone = new PyCloneClone("module", 2L);
                pycloneClone.addSource(new PyCloneSource("file.txt", 15L, 20L, 0.2));
                pycloneClone.addSource(new PyCloneSource("file2.txt", 33L, 45L, 0.1));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 45L, 1L));
            }

            @Test
            public void testMossNicad() {
                assertEquals(mossClone, nicadClone);
            }

            @Test
            public void testMossPyclone() {
                assertEquals(mossClone, pycloneClone);
            }

            @Test
            public void testNicadPyclone() {
                assertEquals(nicadClone, pycloneClone);
            }
        }

        @Nested
        public class NestedTest {
            private Clone mossOuterClone;
            private Clone mossInnerClone;
            private Clone pycloneOuterClone;
            private Clone pycloneInnerClone;
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

                pycloneOuterClone = new PyCloneClone("file", 23L);
                pycloneOuterClone.addSource(new PyCloneSource("b.b", 200L,
                        300L, 0.2));
                pycloneOuterClone.addSource(new PyCloneSource("a.a", 100L,
                        200L, 0.2));

                pycloneInnerClone = new PyCloneClone("file", 2L);
                pycloneInnerClone.addSource(new PyCloneSource("a.a", 155L,
                        160L, 2.1));
                pycloneInnerClone.addSource(new PyCloneSource("b.b", 255L,
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
            public void testMossOuterPycloneInner() {
                assertEquals(mossOuterClone, pycloneInnerClone);
            }

            @Test
            public void testPycloneOuterMossInner() {
                assertEquals(pycloneOuterClone, mossInnerClone);
            }

            @Test
            public void testPycloneOuterNicadInner() {
                assertEquals(pycloneOuterClone, nicadInnerClone);
            }

            @Test
            public void testNicadOuterMossInner() {
                assertEquals(nicadOuterClone, mossInnerClone);
            }

            @Test
            public void testNicadOuterPycloneInner() {
                assertEquals(nicadOuterClone, pycloneInnerClone);
            }
        }

        @Nested
        public class WithinErrorTest {
            private Clone mossClone;
            private Clone pycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.42));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.99));

                pycloneClone = new PyCloneClone("module", 2L);
                pycloneClone.addSource(new PyCloneSource("file.txt", 12L, 17L
                        , 0.2));
                pycloneClone.addSource(new PyCloneSource("file2.txt", 30L,
                        42L, 1.0));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 18L, 23L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 36L, 48L,
                        1L));
            }

            @Test
            public void testAdjustedBefore() {
                assertEquals(mossClone, pycloneClone);
            }

            @Test
            public void testAdjustedAfter() {
                assertEquals(mossClone, nicadClone);
            }

            @Test
            public void testNotWithinRange() {
                assertNotEquals(nicadClone, pycloneClone);
            }
        }

        @Nested
        public class NotEqualsTest {
            private Clone mossClone;
            private Clone pycloneClone;
            private Clone nicadClone;

            @BeforeEach
            public void init() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 20L, 0.89));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.87));

                pycloneClone = new PyCloneClone("module", 2L);
                pycloneClone.addSource(new PyCloneSource("file.txt", 15L,
                        20L, 2.0));
                pycloneClone.addSource(new PyCloneSource("file2.txt", 33L, 45L, 1.0));

                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 45L, 1L));
            }

            @Test
            public void testNotSameFirstFileName() {
                pycloneClone = new PyCloneClone("module", 2L);
                pycloneClone.addSource(new PyCloneSource("filea.txt", 15L,
                        20L, 2.0));
                pycloneClone.addSource(new PyCloneSource("file2.txt", 33L, 45L, 1.0));
                assertNotEquals(mossClone, pycloneClone);
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
                pycloneClone = new PyCloneClone("module", 2L);
                pycloneClone.addSource(new PyCloneSource("file.txt", 15L,
                        20L, 2.0));
                pycloneClone.addSource(new PyCloneSource("file2.txt", 100L, 45L
                        , 1.0));
                assertNotEquals(mossClone, pycloneClone);
            }

            @Test
            public void testNotSameFirstEndLine() {
                mossClone = new MossClone();
                mossClone.addSource(new MossSource("file.txt", 15L, 33L,
                        0.97));
                mossClone.addSource(new MossSource("file2.txt", 33L, 45L,
                        0.99));
                assertNotEquals(pycloneClone, mossClone);
            }

            @Test
            public void testNotSameSecondEndLine() {
                nicadClone = new NiCadClone(33L, 2L);
                nicadClone.addSource(new NiCadSource("file.txt", 15L, 20L, 2L));
                nicadClone.addSource(new NiCadSource("file2.txt", 33L, 33L,
                        1L));
                assertNotEquals(pycloneClone, nicadClone);
            }
        }
    }
}
