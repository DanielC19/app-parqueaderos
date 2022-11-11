package app_parqueaderos;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = menuLogin();
        // Validar que haya un usuario loggeado
        if (user.getName() != null) {
            System.out.println("\n¡Bienvenido " + user.getName() + "!");
            userView(user);
        } else {
            System.out.println("\n¡Bienvenido!");
            guestView();
        }
    }

    public static User menuLogin() {
        Scanner sc = new Scanner(System.in);
        User user = new User();
        int option;
        try {
            Thread.sleep(1500);
        } catch (Exception e) {}
        System.out.println("\n*** U-PARKING ***");
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
                boolean registered = false;
                while (!registered) {
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
                    if (User.register(name, email, password)) {
                        user = new User(name, email, password);
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
        try {
            while (true) {
                // Build all parking
                ArrayList<Parking> parking = Parking.buildParking();
                System.out.println("\n** DISPONIBILIDAD GENERAL **");
                int counter = 1;
                for (Parking item : parking) {
                    System.out.println(counter + ". Parqueadero " + item.getName() + ": " + item.availability());
                    counter++;
                }
                int option;
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
        } catch (Exception e) {
            System.out.println("Hubo un error.");
            System.exit(0);
        }
    }

    public static void userView(User user) {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                // Build all parking
                ArrayList<Parking> parking = Parking.buildParking();
                System.out.println("\n** DISPONIBILIDAD GENERAL **");
                int counter = 1;
                for (Parking item : parking) {
                    System.out.println(counter + ". Parqueadero " + item.getName() + ": " + item.availability());
                    counter++;
                }
                int option;
                do {
                    System.out.println("\nSelecciona el parqueadero que quieras o escribe 0 para salir");
                    System.out.print("Su opción: ");
                    option = sc.nextInt();
                } while (option >= counter);
                if (option == 0) {
                    System.exit(0);
                }
                System.out.println("\n* CELDAS LIBRES EN EL PARQUEADERO " + parking.get(option - 1).getName().toUpperCase() + " *");
                for (Section section : parking.get(option - 1).getSections()) {
                    System.out.println("Sección " + section.getName() + " - Total disponibles: " + section.availability());
                    for (Lot lot : section.getLots()) {
                        String availability;
                        if (!lot.availability()) {
                            if (lot.getUser() != 0) {
                                availability = "reservado";
                            } else {
                                availability = "ocupado";
                            }
                        } else {
                            availability = "disponible";
                        }
                        System.out.println("  - " + lot.getId() + ": " + availability);
                    }
                }
                System.out.println("\nSelecciona el número de celda que quieras reservar o escribe 0 para volver");
                System.out.print("Su opción: ");
                int option2 = sc.nextInt();
                if (option2 == 0) {
                    continue;
                }
                System.out.println("\n¿A qué distancia (en metros) estás de la Universidad?");
                System.out.print("Su respuesta: ");
                int distance = sc.nextInt();
                if (distance > 1500) {
                    System.out.println("\nEstás muy lejos para poder reservar una celda, intenta más tarde\n");
                    continue;
                }
                Lot lot = Lot.findById(option2);
                if (lot.availability()) {
                    lot.reserve(user.getId());
                    System.out.println("\n¡Reservaste con éxito tu celda!");
                    System.out.println("Tienes 2 minutos para parquear en la celda");
                    for (int min = 1; min >= 0; min--) {
                        for (int secs = 59; secs >= 0; secs--) {
                            try {
                                Thread.sleep(999);
                            } catch (Exception e) {}
                            if (secs < 10) {
                                System.out.println(min + ":0" + secs);
                            } else {
                                System.out.println(min + ":" + secs);
                            }
                        }
                    }
                    System.out.println("\nTermino el tiempo, ¿ya llegaste?");
                    System.out.println("Escribe 'true' o 'false'");
                    System.out.print("Su respuesta: ");
                    boolean arrival = sc.nextBoolean();
                    if (arrival) {
                        System.out.println("\n¡Ten un gran día!");
                        break;
                    } else {
                        lot.toggleAvailability();
                        System.out.println("\nLa celda volvió a liberarse, vuelve al inicio\n");
                    }
                } else {
                    System.out.println("\nCelda ocupada o reservada\n");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {}
                }
            }
        } catch (Exception e) {
            System.out.println("Hubo un error.");
            System.exit(0);
        }
    }
}