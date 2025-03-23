package proyecto.algoritmos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.InputStreamReader;

public class Main {
    private static char[][] tablero;
    private static int filas, columnas;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nombreArchivo = "";
        while (true) {
            try {
                limpiarPantalla();
                System.out.print("Ingresa el nombre del archivo (recuerde el .txt): ");
                nombreArchivo = scanner.nextLine();
                generarTablero(nombreArchivo);
                break;
            } catch (Exception e) {
                System.out.println("Ocurrió un error al procesar el archivo. Inténtalo de nuevo.");
            }
        }

        limpiarPantalla();
        System.out.println("El tablero es el siguiente:");
        mostrarTablero();
        System.out.println("\nPresiona 'Enter' para continuar...");
        scanner.nextLine();
        limpiarPantalla();
        int opcion = 0;
        while (true) {
            try {
                System.out.println("¿Quién va a jugar?");
                System.out.println("1. Jugador");
                System.out.println("2. Jugador sintético");
                System.out.print("Selecciona una opción (1 o 2): ");

                opcion = scanner.nextInt();

                if (opcion == 1) {
                    limpiarPantalla();
                    jugadorHumano();
                    break;
                } else if (opcion == 2) {
                    limpiarPantalla();
                    jugadorSintetico();
                    break;
                } else {
                    System.out.println("Opción no válida. Debes seleccionar 1 o 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingresa un número (1 o 2).");
                scanner.next();
            }
        }

        scanner.close();
    }

    public static void generarTablero(String nombreArchivo) {
        ClassLoader classLoader = Main.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(nombreArchivo);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea = br.readLine();
            String[] dimensiones = linea.split(",");
            filas = (Integer.parseInt(dimensiones[0].trim()) * 2) - 1;
            columnas = (Integer.parseInt(dimensiones[1].trim()) * 2) - 1;

            tablero = new char[filas][columnas];

            for (int i = 0; i < filas; i++) {
                if (i % 2 != 0) {
                    for (int j = 0; j < columnas; j++) {
                        tablero[i][j] = '\u25A0';
                    }
                } else {
                    linea = br.readLine();
                    int k = 0;
                    for (int j = 0; j < columnas; j++) {
                        if (j % 2 != 0) {
                            tablero[i][j] = '\u25A0';
                        } else {
                            if (linea.charAt(k) == '0') {
                                tablero[i][j] = '\u25A0';
                            } else {
                                tablero[i][j] = linea.charAt(k);
                            }
                            k++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void mostrarTablero() {
        System.out.print("│");
        for (int i = 1; i <= columnas; i++) {
            if (i >= 10) {
                System.out.print(i + " ");
            } else {
                System.out.print(i + "  ");
            }

        }
        System.out.print("│");
        System.out.println();

        System.out.print("│");
        for (int i = 1; i <= columnas; i++) {
            System.out.print("───");
        }
        System.out.print("│───");

        System.out.println();
        for (int i = 0; i < filas; i++) {
            System.out.print("│");
            for (int j = 0; j < columnas; j++) {
                if (j < columnas - 1) {
                    if (tablero[i][j + 1] == '═') {
                        System.out.print(tablero[i][j] + "══");
                    } else if (tablero[i][j + 1] == '─') {
                        System.out.print(tablero[i][j] + "──");
                    } else if (tablero[i][j] == '═') {
                        System.out.print(tablero[i][j] + "══");
                    } else if (tablero[i][j] == '─') {
                        System.out.print(tablero[i][j] + "──");
                    } else {
                        System.out.print(tablero[i][j] + "  ");
                    }
                } else {
                    System.out.print(tablero[i][j] + "  ");
                }
            }
            System.out.print("│ " + (i + 1));
            System.out.println();
        }
        System.out.print("│");
        for (int i = 1; i <= columnas; i++) {
            System.out.print("───");
        }
        System.out.print("│───");
        System.out.println();
    }

    public static void jugadorHumano() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reglas del juego Hashiwokakero:");
        System.out.println("1. El objetivo es conectar todas las islas usando 'puentes'.");
        System.out
                .println("2. Cada isla contiene un número que indica cuántos puentes deben conectarla a otras islas.");
        System.out.println("3. Las conexiones pueden ser horizontales o verticales, pero nunca en diagonal.");
        System.out.println("4. No se pueden cruzar puentes.");
        System.out.println("5. Puede haber uno o dos puentes entre dos islas, pero nunca más.");
        System.out.println("6. El juego se termina cuando todas las islas están conectadas correctamente.");

        System.out.println("\n¿Cómo jugar?");
        System.out.println("1. Selecciona las coordenadas de la primera isla.");
        System.out.println("2. Selecciona las coordenadas de la segunda isla a la que deseas conectar con un puente.");
        System.out.println("3. El sistema validará si es posible realizar la conexión.");
        System.out.println("4. Continúa hasta conectar todas las islas.");
        System.out.println("\nPresiona 'Enter' para continuar...");
        scanner.nextLine();
        limpiarPantalla();
        mostrarTablero();
        while (true) {
            try {
                System.out.print("Ingresa las coordenadas en el siguiente formato(x1,y1 x2,y2): ");
                String input = scanner.nextLine();
                String[] partes = input.split(" ");
                if (partes.length == 2) {
                    String[] coord1 = partes[0].split(",");
                    String[] coord2 = partes[1].split(",");

                    try {
                        // Convertir las coordenadas a enteros
                        int x1 = Integer.parseInt(coord1[1]) - 1; // Restar 1 para ajustar a índice de array
                        int y1 = Integer.parseInt(coord1[0]) - 1; // Restar 1 para ajustar a índice de array
                        int x2 = Integer.parseInt(coord2[1]) - 1; // Restar 1 para ajustar a índice de array
                        int y2 = Integer.parseInt(coord2[0]) - 1; // Restar 1 para ajustar a índice de array

                        // Verificar si se puede conectar
                        if (esPosibleConectar(x1, y1, x2, y2)) {
                            if (x1 == x2) {
                                conectarHorizontal(x1, y1, y2);
                            } else if (y1 == y2) {
                                conectarVertical(y1, x1, x2);
                            }
                            limpiarPantalla();
                            mostrarTablero();
                        } else {
                            limpiarPantalla();
                            mostrarTablero();
                            System.out.println("No se puede conectar las islas con las coordenadas dadas.");
                        }
                        if (gano()) {
                            limpiarPantalla();
                            mostrarTablero();
                            System.out.println("Todos los puentes han quedado conectados, Ganaste.");
                            break;
                        } else if (!quedanJugadasPosibles()) {
                            limpiarPantalla();
                            mostrarTablero();
                            System.out.println("No quedan Jugadas posibles, Perdiste.");
                            break;
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Coordenadas inválidas. Asegúrate de ingresar números válidos.");
                    }
                } else {
                    System.out.println("Formato inválido. Asegúrate de usar el formato 'x,y x,y'.");
                }
            } catch (Exception e) {
                System.out.println("Formato inválido. Asegúrate de usar el formato 'x,y x,y'.");
            }
        }
        scanner.close();
    }

    public static boolean esPosibleConectar(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return false;
        }
        if (!esIsla(x1, y1) || !esIsla(x2, y2)) {
            return false;
        }
        if (x1 == x2) {
            if (puedeConectarHorizontal(x1, y1, y2)) {
                return true;
            } else {
                return false;
            }
        } else if (y1 == y2) {
            if (puedeConectarVertical(y1, x1, x2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean esIsla(int x, int y) {
        char celda = tablero[x][y];
        return Character.isDigit(celda);
    }

    public static boolean puedeConectarHorizontal(int fila, int y1, int y2) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);

        for (int i = start + 1; i < end; i++) {
            if (Character.isDigit(tablero[fila][i]) || tablero[fila][i] == '\u2502' || tablero[fila][i] == '\u2551'
                    || tablero[fila][i] == '\u2550') {
                return false;
            }
        }
        if (numeroConexionesIsla(fila, y1) >= Character.getNumericValue(tablero[fila][y1]) ||
                numeroConexionesIsla(fila, y2) >= Character.getNumericValue(tablero[fila][y2])) {
            return false;
        }
        return true;
    }

    public static boolean puedeConectarVertical(int columna, int x1, int x2) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);

        for (int i = start + 1; i < end; i++) {
            if (Character.isDigit(tablero[i][columna]) || tablero[i][columna] == '\u2500'
                    || tablero[i][columna] == '\u2550' || tablero[i][columna] == '\u2551') {
                return false;
            }
        }
        if (numeroConexionesIsla(x1, columna) >= Character.getNumericValue(tablero[x1][columna])
                || numeroConexionesIsla(x2, columna) >= Character.getNumericValue(tablero[x2][columna])) {
            return false;
        }
        return true;
    }

    public static int numeroConexionesIsla(int x, int y) {
        int conexiones = 0;
        // Izquierda
        if (y - 1 >= 0 && tablero[x][y - 1] == '\u2500')
            conexiones++;
        else if (y - 1 >= 0 && tablero[x][y - 1] == '\u2550')
            conexiones += 2;
        // Derecha
        if (y + 1 < columnas && tablero[x][y + 1] == '\u2500')
            conexiones++;
        else if (y + 1 < columnas && tablero[x][y + 1] == '\u2550')
            conexiones += 2;
        // Arriba
        if (x - 1 >= 0 && tablero[x - 1][y] == '\u2502')
            conexiones++;
        else if (x - 1 >= 0 && tablero[x - 1][y] == '\u2551')
            conexiones += 2;
        // Abajo
        if (x + 1 < filas && tablero[x + 1][y] == '\u2502')
            conexiones++;
        else if (x + 1 < filas && tablero[x + 1][y] == '\u2551')
            conexiones += 2;
        return conexiones;
    }

    public static boolean conectarHorizontal(int fila, int y1, int y2) {
        int start = Math.min(y1, y2);
        int end = Math.max(y1, y2);
        for (int i = start + 1; i < end; i++) {
            if (tablero[fila][i] == '\u25A0') {
                tablero[fila][i] = '\u2500';
            } else if (tablero[fila][i] == '\u2500') {
                tablero[fila][i] = '\u2550';
            }
        }
        return true;
    }

    public static boolean conectarVertical(int columna, int x1, int x2) {
        int start = Math.min(x1, x2);
        int end = Math.max(x1, x2);
        for (int i = start + 1; i < end; i++) {
            if (tablero[i][columna] == '\u25A0') {
                tablero[i][columna] = '\u2502';
            } else if (tablero[i][columna] == '\u2502') {
                tablero[i][columna] = '\u2551';
            }
        }
        return true;
    }

    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void jugadorSintetico() {
        System.out.println("Iniciando jugador sintético...");
        mostrarTablero();

        while (true) {
            boolean hizoMovimiento = false;

            // Lista para almacenar islas pendientes de conexiones
            List<int[]> islasPendientes = new ArrayList<>();

            // Recopilar todas las islas que necesitan más conexiones
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (Character.isDigit(tablero[i][j])
                            && numeroConexionesIsla(i, j) < Character.getNumericValue(tablero[i][j])) {
                        islasPendientes.add(new int[] { i, j });
                    }
                }
            }

            // Ordenar islas por mayor número de conexiones necesarias
            islasPendientes.sort((a, b) -> Integer.compare(
                    Character.getNumericValue(tablero[b[0]][b[1]]) - numeroConexionesIsla(b[0], b[1]),
                    Character.getNumericValue(tablero[a[0]][a[1]]) - numeroConexionesIsla(a[0], a[1])));

            // Intentar conectar cada isla en orden de prioridad
            for (int[] isla : islasPendientes) {
                int i = isla[0];
                int j = isla[1];

                // Intentar hacer conexiones hasta que se complete el número necesario
                while (numeroConexionesIsla(i, j) < Character.getNumericValue(tablero[i][j])) {
                    boolean conectado = false;

                    // Intentar conexión hacia derecha
                    for (int k = j + 2; k < columnas; k += 2) {
                        if (Character.isDigit(tablero[i][k])) {
                            if (esPosibleConectar(i, j, i, k)) {
                                conectarHorizontal(i, j, k);
                                conectado = true;
                                mostrarTablero();
                            }
                            break;
                        }
                    }

                    // Intentar conexión hacia abajo
                    for (int k = i + 2; k < filas; k += 2) {
                        if (Character.isDigit(tablero[k][j])) {
                            if (esPosibleConectar(i, j, k, j)) {
                                conectarVertical(j, i, k);
                                conectado = true;
                                mostrarTablero();
                            }
                            break;
                        }
                    }

                    // Intentar conexión hacia izquierda
                    for (int k = j - 2; k >= 0; k -= 2) {
                        if (Character.isDigit(tablero[i][k])) {
                            if (esPosibleConectar(i, j, i, k)) {
                                conectarHorizontal(i, k, j);
                                conectado = true;
                                mostrarTablero();
                            }
                            break;
                        }
                    }

                    // Intentar conexión hacia arriba
                    for (int k = i - 2; k >= 0; k -= 2) {
                        if (Character.isDigit(tablero[k][j])) {
                            if (esPosibleConectar(i, j, k, j)) {
                                conectarVertical(j, k, i);
                                conectado = true;
                                mostrarTablero();
                            }
                            break;
                        }
                    }

                    // Si no se pudo hacer ninguna conexión, salir del ciclo
                    if (!conectado) {
                        break;
                    } else {
                        hizoMovimiento = true;
                    }
                }
            }

            // Terminar si no se hizo ningún movimiento
            if (!hizoMovimiento) {
                System.out.println("No quedan movimientos posibles.");
                break;
            }

            // Verificar si se ha ganado el juego
            if (gano()) {
                System.out.println("El jugador sintético ha ganado el juego.");
                break;
            }
        }
    }

    public static boolean quedanJugadasPosibles() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (Character.isDigit(tablero[i][j])) {
                    for (int k = 0; k < columnas; k++) {
                        if (Character.isDigit(tablero[i][k]) && esPosibleConectar(i, j, i, k)) {
                            return true;
                        }
                    }
                    for (int k = 0; k < filas; k++) {
                        if (Character.isDigit(tablero[k][j]) && esPosibleConectar(i, j, k, j)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean gano() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (Character.isDigit(tablero[i][j])) {
                    if (numeroConexionesIsla(i, j) != Character.getNumericValue(tablero[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
// en el codigo x es y y es x tener encuenta!