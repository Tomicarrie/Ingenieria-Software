package anillo;

import java.util.Stack;
import java.util.concurrent.Callable;

public abstract class Link {
    public Object cargo;
    public Link next_link;
    public Link prev_link;
    
    public abstract Link next();
    public abstract Link add( Object cargo, Stack<Callable<Link>> stack, Ring ring);
    public abstract Object current();
    public abstract Link remove();
}
