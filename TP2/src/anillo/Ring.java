
package anillo;

import org.junit.jupiter.api.function.Executable;

import java.util.Stack;
import java.util.concurrent.Callable;

public class Ring {
    public Link current;
    public Stack<Callable<Link>>stack;

    public Ring() {
        current = new nullLink();
        stack = new Stack<>();
        stack.push(() -> current.remove());
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
        try {
            Callable<Link> f = stack.pop();
            current = f.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return this;
    }
}
