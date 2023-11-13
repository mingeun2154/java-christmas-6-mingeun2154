package christmas.study;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashSet;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ObjectEqualityStudy {

    private class Bar {
        protected final String name;
        protected final Integer quantity;

        public Bar(String name, Integer quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Bar bar = (Bar) o;
            return Objects.equals(name, bar.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    private class Foo extends Bar {
        public Foo(String name, Integer quantity) {
            super(name, quantity);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Foo foo = (Foo) o;
            return Objects.equals(name, foo.name) && Objects.equals(quantity, foo.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, quantity);
        }
    }

    @DisplayName("일부 필드가 일치하면 동등")
    @Test
    void equalityCheckByPartialData() {
        // 값 동등성
        Bar pasta1 = new Bar("pasta", 100);
        Bar pasta2 = new Bar("pasta", 23);
        Bar pasta3 = new Bar("pizza", 100);
        assertThat(pasta1).isEqualTo(pasta2);
        assertThat(pasta3).isNotEqualTo(pasta1);
        // hash 동등성
        HashSet<Bar> set = new HashSet<>();
        set.add(pasta1);
        assertThat(set.contains(pasta2)).isTrue();
        assertThat(set.contains(pasta3)).isFalse();
    }

    @DisplayName("모든 필드가 일치해야 동등")
    @Test
    void equalityCheckByEveryData() {
        // 값 동등성
        Foo foo1 = new Foo("foo", 123);
        Foo foo2 = new Foo("foo", 10);
        Foo foo3 = new Foo("foo", 123);
        assertThat(foo1).isEqualTo(foo3);
        assertThat(foo1).isNotEqualTo(foo2);
        // hash 동등성
        HashSet<Foo> set = new HashSet<>();
        set.add(foo1);
        assertThat(set.contains(foo2)).isFalse();
        assertThat(set.contains(foo3)).isTrue();
    }
}
