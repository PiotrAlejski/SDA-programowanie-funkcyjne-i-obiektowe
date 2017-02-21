package sda.finances;

import sda.finances.model.Expense;
import sda.finances.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Expense> expenses = init();

        //1. wyswietlic wszystkie towary ktorych cena jednostkowa jest mniejsza od 3

        expenses.forEach(expense -> {
            expense.getProducts().stream()
                    .filter(product -> product.getUnitPrice() <= 3)
                    .forEach(product -> System.out.println(product));
        });
        System.out.println();

        //2. pokaz tylko produkty typu spozywcze ktorych cena jednostkowa jest mniejsza niz 3

        expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .forEach(expense -> {
                    expense.getProducts().stream()
                            .filter(product -> product.getUnitPrice() <= 3)
                            .forEach(product -> System.out.println(product));
                });
        System.out.println();

        //3. wyswietlamy tylko banany
        expenses.forEach(expense -> {
            expense.getProducts().stream()
                    .filter(product -> product.getName().equals("banan"))
                    .forEach(product -> System.out.println(product));
        });
        System.out.println();

        //!!!!!!!!!!!!!!!!!!!!!!!!!!banany + cena

        System.out.println("BANANY");
        double banan = expenses.stream()
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().equals("banan"))
                        .mapToDouble(product -> product.getUnitPrice() * product.getAmount())
                        .sum()
                )
                .sum();
        System.out.println(banan);
        System.out.println();

        //4. suma cen wszystkich produktow spozywczych

        expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .forEach(expense -> {
                    System.out.println(expense.getProducts().stream()
                            .mapToDouble(Product::getUnitPrice).sum());

                });

        System.out.println();
        System.out.println(expenses.stream()
                .filter(expense -> expense.getType().equals("spozywcze"))
                .mapToDouble(Expense::getPrice).sum());
        //5.produkt kupione przed 19 luty
        expenses.stream()
                .filter(expense -> expense.getDate().isBefore(LocalDate.of(2017, 2, 19)))
                .forEach(expense -> expense.getProducts()
                        .forEach(product -> System.out.println(product)));

        //6. wyswietlic wydatki dla konkretnego dnia
        //   (ilosc kupionych produktow, ilosc wydanych pieniedzy)
        expenses.stream()
                .filter(expense -> expense.getDate().isEqual(LocalDate.of(2017, 2, 21)))
                .mapToDouble(expense -> expense.getPrice())
                .sum();

        System.out.println();
        // 6.1 wyswietl tylko wydatki na piwo dla konkretnego dnia
        //    (ilosc kupionych produktow ilosc wydanych pieniedzy)
        expenses.stream()
                .filter(expense -> expense.getDate().isEqual(LocalDate.of(2017, 2, 21)))
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().equals("Piwo"))
                        .mapToDouble(product -> product.getUnitPrice() * product.getAmount())
                        .sum());
        //7.zsumowac calkowite kwote wydana na produkty zaczynajace sie na p.
        System.out.println();
        System.out.println(expenses.stream()
                .mapToDouble(expense -> expense.getProducts()
                        .stream()
                        .filter(product -> product.getName().startsWith("p"))
                        .mapToDouble(product -> product.getAmount() * product.getUnitPrice())
                        .sum())
                .sum());

        //8. Z sumowac koszt produktow, ktore kupilismy w parzystych ilosciach.
        System.out.println(expenses.stream()
                .filter(expense -> expense.getType().equals("spożywcze"))
                .mapToDouble(expense -> expense.getProducts().stream()
                        .filter(product -> product.getAmount() % 2 == 0)
                        .mapToDouble(product -> product.getAmount() * product.getUnitPrice())
                        .sum())
                .sum());

        //9. Z kazdego expensa wyciagnac produkt za ktorego zaplacimy najwiecej.
        //            (amount _ unitprice)
        expenses.stream()
                .map(expense -> expense.getProducts()
                        .stream()
                        .max((e1, e2) ->
                (e1.getUnitPrice() * e1.getAmount()) >
                (e2.getAmount() * e2.getUnitPrice()) ? 1 : -1)
        .get())
        .forEach(product -> System.out.println());
        //10 wyswietlic najdrozszy product z wszystkich expoensow
        System.out.println(expenses.stream()
                .map(expense -> expense.getProducts()
                        .stream()
                        .max((e1, e2) ->
                                (e1.getUnitPrice() * e1.getAmount()) >
                                        (e2.getAmount() * e2.getUnitPrice()) ? 1 : -1)
                        .get())
                .max((e1, e2) ->
                        (e1.getUnitPrice() * e1.getAmount()) >
                                (e2.getAmount() * e2.getUnitPrice()) ? 1 : -1)
                .get());


    }

    private static List<Expense> init() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("banan", 5, 2));
        products.add(new Product("piwo", 2, 4));
        products.add(new Product("pomarancza", 10, 0.5));
        products.add(new Product("chipsy", 1, 5));

        Expense expense = new Expense("spozywcze", products);

        List<Product> products2 = new ArrayList<>();
        products2.add(new Product("farba", 1, 25));
        products2.add(new Product("mlotek", 2, 10));
        products2.add(new Product("gwozdzie", 100, 0.1));

        Expense expense2 = new Expense("budowlane", products2, 2017, 2, 19);

        List<Product> products3 = new ArrayList<>();
        products3.add(new Product("witamina C", 2, 3));
        products3.add(new Product("apap", 1, 10));
        products3.add(new Product("syrop", 1, 5));

        Expense expense3 = new Expense("lekarstwa", products3, 2017, 2, 18);

        List<Product> products4 = new ArrayList<>();
        products4.add(new Product("cytryna", 4, 2.5));
        products4.add(new Product("chleb", 2, 2));
        products4.add(new Product("miesa", 2, 15));

        Expense expense4 = new Expense("spozywcze", products4, 2017, 2, 17);


        return Arrays.asList(expense, expense2, expense3, expense4);
    }
}