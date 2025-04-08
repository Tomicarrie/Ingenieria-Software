//package anillo;
//import java.util.ArrayList;
//
//
//public class Ring {
//
//    private ArrayList<Object> anillo = new ArrayList<Object>();
//    private Integer curr = (-1);
//
//    public Ring next() {
//        try {
//            this.curr = (this.curr + 1) % this.anillo.size();
//            return this;
//
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }
//
//    public Object current() {
//        try {
//            return this.anillo.get(this.curr);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
//    }
//
//    public Ring add( Object cargo ) {
//
//        ArrayList<Object> newRing = new ArrayList<Object>();
//        newRing.add(cargo);
//        int size = this.anillo.size();
//
//        for (int i = 0; i < size; i++) {
//            newRing.add(this.anillo.get((i + this.curr) % size));
//        }
//
//        this.curr = 0;
//        this.anillo = newRing;
//        return this;
//
//    }
//
//    public Ring remove() {
//        ArrayList<Object> newRing = new ArrayList<Object>();
//        int size = this.anillo.size();
//
//        for (int i = 0; i < size - 1; i++) {
//            newRing.add(this.anillo.get((i + this.curr + 1) % size));
//        }
//
//        this.curr = 0;
//        this.anillo = newRing;
//        return this;
//    }
//}

package anillo;


class nullLink extends Link {

//    public nullLink() {
//        super();
//        cargo = null;
//        next_link = null;
//        prev_link = null;
//    }

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

    public Link add( Object cargo ) {
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
    public Link add( Object cargo) {
        cargoLink newLink = new cargoLink(cargo);
        newLink.next_link = this;
        newLink.prev_link = this.prev_link;

        this.prev_link.next_link = newLink;
        this.prev_link = newLink;
        return newLink;
    }

    public Link next() {
        return this.next_link;
    }

    public Link remove() {
        if ( this.next_link == this ) {
            return new nullLink();
        }
        this.prev_link.next_link = this.next_link;
        this.next_link.prev_link = this.prev_link;
        // destruir ??
        return this.next_link;
    }

    public Object current() {
        return this.cargo;
    }
}

public class Ring {
    public Link current = new nullLink();

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add( Object cargo ) {
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }
}
