
package anillo;
import java.util.Stack;
import java.util.function.Function;

public class Ring {

    private Link current;
    private Stack<Function<Link, Link>> stackForRemoves;

    public Ring() {
        current =  new nullLink();
        stackForRemoves = new Stack<>();
        stackForRemoves.push(link -> current.remove());
    }

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add( Object cargo ) {
        Link prev_current = current;
        stackForRemoves.push((Link link) -> prev_current.logForRemove(link));
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        Function<Link, Link> removeFunction = stackForRemoves.pop();
        current = removeFunction.apply(current);
        return this;
    }
}
