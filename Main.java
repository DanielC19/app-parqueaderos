package app_parqueaderos;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = menuLogin();
        // Validar que haya un usuario loggeado
        if (user.getName() != null) {
            System.out.println("\n¡Bienvenido " + user.getName() + "!");
        } else {
            System.out.println("\n¡Bienvenido!");
        }
        guestView();
    }

    public static User menuLogin() {
        Scanner sc = new Scanner(System.in);
        User user = new User();
        int option = 0;
        try {
            Thread.sleep(1500);
        } catch (Exception e) {}
        System.out.println("\n*** APP PARQUEADEROS ***");
        System.out.println("------------------------");
        System.out.println("Presiona 1 para iniciar sesión");
        System.out.println("Presiona 2 para registrarte");
        System.out.println("Presiona 3 para continuar");
        System.out.println("Presiona 0 para salir");
        System.out.print("Su opción: ");
        // Pedir acción al usuario
        option = sc.nextInt();
        System.out.println("\n");
        boolean validate = false;
        String name = "";
        String email = "";
        String password = "";
        switch (option) {
            case 1:
                System.out.println("*** INICIO DE SESIÓN ***");
                boolean logged = false;
                int tries = 0;
                while (!logged && (tries < 3)) {
                    tries++;
                    validate = false;
                    while (!validate) {
                        System.out.print("Email: ");
                        email = sc.next();
                        if (email.length() < 255) {
                            validate = true;
                        } else {
                            System.out.println("El email debe contener menos de 255 caracteres");
                        }
                    }
                    validate = false;
                    while (!validate) {
                        System.out.print("Contraseña: ");
                        password = sc.next();
                        if (password.length() <= 50 && password.length() >= 8) {
                            validate = true;
                        } else {
                            System.out.println("La contraseña debe contener entre 8 y 50 caracteres");
                        }
                    }
                    if (User.login(email, password)) {
                        user = new User(email, password);
                        logged = true;
                    } else {
                        System.out.println("Error al iniciar sesión");
                    }
                }
                if (tries >= 3) {
                    System.out.println("Demasiados intentos fallidos, intenta más tarde");
                    System.exit(0);
                }
                break;
            // Registro
            case 2:
                System.out.println("*** REGISTRO ***");
                boolean vehicle = true;
                boolean registered = false;
                while (!registered) {
                    while (!validate) {
                        System.out.print("Email: ");
                        email = sc.next();
                        if (email.length() < 255) {
                            validate = true;
                        } else {
                            System.out.println("El email debe contener menos de 255 caracteres");
                        }
                    }
                    validate = false;
                    while (!validate) {
                        System.out.print("Contraseña: ");
                        password = sc.next();
                        if (password.length() <= 50 && password.length() >= 8) {
                            validate = true;
                        } else {
                            System.out.println("La contraseña debe contener entre 8 y 50 caracteres");
                        }
                    }
                    validate = false;
                    while (!validate) {
                        System.out.print("Nombre: ");
                        name = sc.next();
                        if (name.length() < 255) {
                            validate = true;
                        } else {
                            System.out.println("El nombre debe contener menos de 255 caracteres");
                        }
                    }
                    System.out.print("Vehículo: ");
                    System.out.print("(Escribe 'true' para carro y 'false' para moto)\n");
                    vehicle = sc.nextBoolean();
                    if (User.register(name, email, password, vehicle)) {
                        user = new User(name, email, password, vehicle);
                        registered = true;
                    } else {
                        System.out.println("Error al registrarse");
                    }
                }
                break;
            case 3:
                break;
            default:
                System.out.println("¡Vuelve pronto!");
                System.exit(0);
                break;
        }
        return user;
    }

    public static void guestView() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            // Build all parking
            ArrayList<Parking> parking = Parking.buildParking();
            System.out.println("\n** DISPONIBILIDAD GENERAL **");
            int counter = 1;
            for (Parking item : parking) {
                System.out.println(counter + ". Parqueadero " + item.getName() + ": " + item.availability());
                counter++;
            }
            int option = 0;
            do {
                System.out.println("\nSelecciona el parqueadero que quieras o escribe 0 para salir");
                System.out.print("Su opción: ");
                option = sc.nextInt();
            } while (option >= counter);
            if (option == 0) {
                System.exit(0);
            }
            System.out.println("\n* DISPONIBILIDAD PARQUEADERO " + parking.get(option - 1).getName().toUpperCase() + " *");
            for (Section section : parking.get(option - 1).getSections()) {
                System.out.println("Sección " + section.getName() + ": " + section.availability());
            }
            System.out.println("\nPresiona 0 para salir o cualquier otra tecla para volver");
            System.out.print("Su opción: ");
            String option2 = sc.next();
            if (option2.equals("0")) {
                System.exit(0);
            }
        }
    }
}
