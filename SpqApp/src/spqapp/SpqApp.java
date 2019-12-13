/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spqapp;

import java.util.ArrayList;

/**
 *
 * @author Manno
 */
public class SpqApp {


    public static void main(String[] args) {
       ArrayList<SPQ.InputElement> inputList=new ArrayList();
       ArrayList<Character> wList=new ArrayList<>();
       wList.add('a');
       wList.add('c');
       inputList.add(new SPQ.InputElement(1, wList));
       
       wList=new ArrayList<>();
       wList.add('b');
       wList.add('c');
       inputList.add(new SPQ.InputElement(2, wList));
       
       wList=new ArrayList<>();
       wList.add('a');
       wList.add('b');
       inputList.add(new SPQ.InputElement(3, wList));
       
       wList=new ArrayList<>();
       wList.add('a');
       wList.add('c');
       inputList.add(new SPQ.InputElement(3.5, wList));
       
       wList=new ArrayList<>();
       wList.add('b');
       wList.add('c');
       inputList.add(new SPQ.InputElement(4, wList));
       
       wList=new ArrayList<>();
       wList.add('a');
       wList.add('b');
       inputList.add(new SPQ.InputElement(5, wList));
       
       wList=new ArrayList<>();
       wList.add('a');
       wList.add('c');
       inputList.add(new SPQ.InputElement(5.5, wList));
       
       new SPQ(inputList).printTheResult();
       
        
    }
    
}
