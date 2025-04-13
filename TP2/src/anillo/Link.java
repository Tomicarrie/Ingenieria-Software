package anillo;

import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Function;

public abstract class Link {
    private Object cargo;
    private Link next_link;
    private Link prev_link;

    public Object getCargo() {
        return cargo;
    }
    public Link getNext_link() {
        return next_link;
    }
    public Link getPrev_link() {
        return prev_link;
    }

    public void setCargo(Object cargo) {
        this.cargo = cargo;
    }
    public void setNext_link(Link next_link) {
        this.next_link = next_link;
    }
    public void setPrev_link(Link prev_link) {
        this.prev_link = prev_link;
    }

    public void assignNextAndPrev( Link link, Link nextLink, Link prevLink) {
        link.setNext_link(nextLink);
        link.setPrev_link(prevLink);

    }

    public abstract Link next();
    public abstract Link add( Object cargo, Stack<Function<Link, Link>> stack, Ring ring);
    public abstract Object current();
    public abstract Link remove();
}

class nullLink extends Link {

    private Object cargo = null;
    private Link next_link = null;
    private Link prev_link = null;

    public Link next() {
        throw new RuntimeException();
    }

    public Object current() {
        throw new RuntimeException();
    }

    public Link remove() {
        return new nullLink();
    }

    public Link add( Object cargo, Stack<Function<Link, Link>> stack, Ring ring) {
        cargoLink newLink = new cargoLink(cargo);
        assignNextAndPrev(newLink, newLink, newLink);
        stack.push((link) -> new nullLink());
        return newLink;
    }
}

class cargoLink extends Link {
    public cargoLink(Object cargo) {
        super();
        setCargo(cargo);
        setNext_link(null);
        setPrev_link(null);
    }

    public Link add(Object cargo, Stack<Function<Link, Link>> stack, Ring ring) {

        cargoLink newLink = new cargoLink(cargo);
        assignNextAndPrev(newLink, this, this.getPrev_link());

        getPrev_link().setNext_link(newLink);
        setPrev_link(newLink);

        stack.push((Link link) -> link.remove());

        return newLink;
    }

    public Link next() {
        return getNext_link();
    }

    public Link remove() {
        getPrev_link().setNext_link(getNext_link());
        getNext_link().setPrev_link(getPrev_link());
        return getNext_link();
    }

    public Object current() {
        return getCargo();
    }
}