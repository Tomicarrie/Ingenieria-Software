
package anillo;

import org.junit.jupiter.api.function.Executable;
import org.junit.platform.engine.support.hierarchical.Node;

import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Ring {
    public Link current;
    public Stack<Function<Link, Link>> stack;

    public Ring() {
        current = new nullLink();
        stack = new Stack<>();
        stack.push((link) -> link.remove());

    }

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add( Object cargo ) {
        current = current.add(cargo, stack, this);
        return this;

    }

    public Ring remove() {

        Function<Link, Link> f = stack.pop();
        current = f.apply(current);

        return this;
    }
}
