package com.pagination.demo.model;

import java.util.Comparator;

public class Parent {
    public Integer id;
    public String sender;
    public String receiver;
    public Integer totalAmount;
    public Integer totalPaidAmount;

    public Integer getId() {
        return id;
    }

    public void setTotalPaidAmount(Integer totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public static Comparator<Parent> idAesComparator = new Comparator<Parent>() {

        public int compare(Parent p1, Parent p2) {
            Integer id1 = p1.getId();
            Integer id2 = p2.getId();

            //ascending order
            return id1.compareTo(id2);
        }};

    public static Comparator<Parent> idDesComparator = new Comparator<Parent>() {

        public int compare(Parent p1, Parent p2) {
            Integer id1 = p1.getId();
            Integer id2 = p2.getId();

            //descending order
            return id2.compareTo(id1);
        }};

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalPaidAmount=" + totalPaidAmount +
                '}';
    }
}
