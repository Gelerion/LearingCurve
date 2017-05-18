package com.denis.learning.rpc.invoke.serialization;

//Not serializable
public class Manager {
    //Transient field wont work here as Manager is not implementing Serializable
    public transient String managerName = "Manager Name";
}
