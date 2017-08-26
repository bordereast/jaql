package com.bordereast.jaql.arango;

public class Filter {
    
    public Logical logcial;
    public Operator operatorA;
    public Operator operatorB;
    public String left;
    public String right;
    
    public Filter(Logical logical, String left, Operator operator, String right) {
        this.left = left;
        this.right = right;
        this.operatorA = operator;
        this.operatorB = Operator.blank;
        this.logcial = logical;
    }
    
    public Filter(Logical logical, String left, Operator operatorA, Operator operatorB, String right) {
        this.left = left;
        this.right = right;
        this.operatorA = operatorA;
        this.operatorB = operatorB;
        this.logcial = logical;
    }
}
