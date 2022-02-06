package com.pagination.demo.model;

import java.util.Comparator;

public class Child {
    public Integer id;
    public Integer parentId;
    public String sender;
    public String receiver;
    public Integer paidAmount;

    public Integer getId() {
        return id;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public static Comparator<Child> idAesComparator = new Comparator<Child>() {

        public int compare(Child c1, Child c2) {
            Integer id1 = c1.getId();
            Integer id2 = c2.getId();

            //ascending order
            return id1.compareTo(id2);
        }};

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", paidAmount=" + paidAmount +
                '}';
    }
}
