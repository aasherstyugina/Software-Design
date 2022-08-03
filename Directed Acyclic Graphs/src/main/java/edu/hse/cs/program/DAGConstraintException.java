package edu.hse.cs.program;

/**
 * Custom exception for Directed Acyclic Graphs. It's a subclass of Exception.
 * @author Sherstyugina Anastasia, BSE204.
 */
public class DAGConstraintException extends Exception{
    public DAGConstraintException(String message){
        super(message);
    }
}
