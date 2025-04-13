package anillo;

import java.util.Stack;
import java.util.function.Function;

public abstract class Link {
    private Object cargo;
    private Link next_link;
    private Link prev_link;

    // accessors
    public Object getCargo() {return cargo;}
    public Link getNext_link() {
        return next_link;
    }
    public Link getPrev_link() {
        return prev_link;
    }

    //setters
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
    public abstract Link add( Object cargo);
    public abstract Object current();
    public abstract Link remove();
    public abstract Link logForRemove(Link link);
}

class nullLink extends Link {

    public Link next() {
        throw new RuntimeException("No se puede obtener el siguiente de un anillo vacio");
    }

    public Object current() {
        throw new RuntimeException( "No se puede extraer el current de un anillo vacio");
    }

    public Link remove() {
        throw new RuntimeException("No se puede remover de un anillo vacio");
    }

    public Link add( Object cargo) {
        cargoLink newLink = new cargoLink(cargo);
        assignNextAndPrev(newLink, newLink, newLink);
        return newLink;
    }

    public Link logForRemove(Link link) {
        return this;
    }
}

class cargoLink extends Link {
    public cargoLink(Object cargo) {
        super();
        setCargo(cargo);
        setNext_link(null);
        setPrev_link(null);
    }

    public Link add(Object cargo) {

        cargoLink newLink = new cargoLink(cargo);
        assignNextAndPrev(newLink, this, this.getPrev_link());

        getPrev_link().setNext_link(newLink);
        setPrev_link(newLink);

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

    public Link logForRemove(Link link) {
        return link.remove();
    }
}