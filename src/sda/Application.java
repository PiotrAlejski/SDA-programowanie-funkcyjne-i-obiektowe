package sda;

import sda.workers.AbstractEmployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by RENT on 2017-02-18.
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World");

        List<AbstractEmployee> employeeList = new ArrayList<>();
        employeeList.add(new AbstractEmployee("Szymon", "Nowak", 2000, "JAVA"));
        employeeList.add(new AbstractEmployee("Jan", "Kowalski", 5000, "JAVA"));
        employeeList.add(new AbstractEmployee("Piotr", "Alejski", 5500, "HR"));
        employeeList.add(new AbstractEmployee("Paweł", "Pawlak", 6000, "HR"));
        employeeList.add(new AbstractEmployee("Anna", "Zając", 6000, "HR"));
        //1.Za pomoca filter wypisac tylko ludzi z dzialu JAVA
        employeeList.stream()
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println(e));
        //2. Wypisz imiona kobiet
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getFirstName().endsWith("a"))
                .forEach(e -> System.out.println(e));
        //3. Za pomocą filter wypisac osoby pow.3000
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getSalary() > 3000)
                .forEach(e -> System.out.println());
        //4. za pomoca filter
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getSalary() >= 3000)
                .filter(e -> e.getDepartment().equals("JAVA"))
                .forEach(e -> System.out.println());
        System.out.println();
        //5. Za pomoca Filter oraz??
        //6. Szukam pracowniaka po nazwisku
        System.out.println();
        employeeList.stream()
                .filter(e-> e.getLastName().equals("NOWAK"))
        .forEach(e -> System.out.println(e));
        //7. Szukam pracownika po nazwisku (metoda startsWith)
        System.out.println();
        employeeList.stream()
                .filter(e -> e.getLastName().startsWith("NOW"))
                .forEach(e -> System.out.println(e));
        //8. Dzileimy liste na mape ludzi gdzie klucz to ich imie, a wartosc
        System.out.println();
        Map<String, AbstractEmployee> map= employeeList.stream()
                .collect(Collectors.toMap((e-> e.getFirstName()), e -> e));
        map.forEach((k,v) -> System.out.println(k + " : " + v));
        //9. zwracamy liste employee szukanej po imie + "" + nazwisko
        System.out.println();
        employeeList.stream()
                .filter(e -> (e.getFirstName() + " " + e.getLastName()).equals("Szymon Nowak"))
                .forEach(e -> System.out.println(e));
        //10. posortuj po salary
        employeeList.sort((e1, e2) -> e1.getSalary()> e2.getSalary() ? 1: -1);
        employeeList.forEach(e -> System.out.println(e.getFirstName()+ ": " + e.getSalary()));
        //11. wyswietl employee ktory zarabia najwiecej
        System.out.println();
        employeeList.sort((e1, e2) -> e1.getSalary() < e2.getSalary()? 1: -1);
        System.out.println(employeeList.get(0));

        System.out.println();
        AbstractEmployee richestEmployee = employeeList.stream()
                .max((e1, e2) -> e1.getSalary() > e2.getSalary() ? 1: -1)
                .get();
        System.out.println(richestEmployee);
        //12. wyswietl employee ktory zarabia najmniej
        System.out.println();
        AbstractEmployee poorestEmployee = employeeList.stream()
                .min((e1,e2) -> e1.getSalary() > e2.getSalary() ? 1:-1)
                .get();
        System.out.println(poorestEmployee);

    }

}
