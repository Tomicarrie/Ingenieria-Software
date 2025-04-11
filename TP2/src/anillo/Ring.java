
package anillo;

import org.junit.jupiter.api.function.Executable;

import java.util.Stack;
import java.util.concurrent.Callable;

class nullLink extends Link {

    public Object cargo = null;
    public Link next_link = null;
    public Link prev_link = null;

    public Link next() {
        throw new RuntimeException();
    }

    public Object current() {
        throw new RuntimeException();
    }

    public Link remove() {
        throw new RuntimeException();
    }

    public Link add( Object cargo, Stack<Callable<Link>> stack, Ring ring) {
        cargoLink newLink = new cargoLink(cargo);
        newLink.next_link = newLink;
        newLink.prev_link = newLink;
        return newLink;
    }
}

class cargoLink extends Link {
    public cargoLink(Object cargo) {
        super();
        this.cargo = cargo;
        this.next_link = null;
        this.prev_link = null;
    }
    public Link add( Object cargo, Stack<Callable<Link>> stack, Ring ring) {
        cargoLink newLink = new cargoLink(cargo);
        newLink.next_link = this;
        newLink.prev_link = this.prev_link;

        this.prev_link.next_link = newLink;
        this.prev_link = newLink;

        stack.push(() -> ring.current.remove());

        return newLink;
    }

    public Link next() {
        return this.next_link;
    }

    public Link remove() {
        this.prev_link.next_link = this.next_link;
        this.next_link.prev_link = this.prev_link;
        return this.next_link;
    }

    public Object current() {
        return this.cargo;
    }
}

public class Ring {
    public Link current;
    public Stack< Callable<Link>>stack;

    public Ring() {
        current = new nullLink();
        stack = new Stack<>();
        stack.push(() -> new nullLink());
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
