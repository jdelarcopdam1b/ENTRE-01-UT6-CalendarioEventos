package programacion.entregaut6.interfaz;

import programacion.entregaut6.io.CalendarioIO;
import programacion.entregaut6.modelo.CalendarioEventos;
import programacion.entregaut6.modelo.Mes;

import java.util.Scanner;

public class IUConsola {
    private static final int MOSTRAR_CALENDARIO = 1;
    private static final int TOTAL_EVENTOS_MES = 2;
    private static final int MESES_CON_MAS_EVENTOS = 3;
    private static final int EVENTO_MAS_LARGO = 4;
    private static final int CANCELAR_EVENTOS = 5;
    private static final int SALIR = 6;

    private Scanner teclado;
    private CalendarioEventos calendario;

    /**
     * Constructor de la clase programacion.entregaut6.interfaz.IUConsola
     */
    public IUConsola(CalendarioEventos calendario) {
        teclado = new Scanner(System.in);
        this.calendario = calendario;
        CalendarioIO.cargarEventos(calendario);

    }

    /**
     *  Incluye la l�gica de la aplicaci�n
     *  
     */
    public void iniciar() {
        int opcion = menu();
        while (opcion != SALIR) {
            switch (opcion) {
                case MOSTRAR_CALENDARIO:
                mostrarCalendario();
                break;
                case TOTAL_EVENTOS_MES:
                totalEventosMes();
                break;
                case MESES_CON_MAS_EVENTOS:
                mesesConMasEventos();
                break;
                case EVENTO_MAS_LARGO:
                eventoMasLargo();
                break;
                case CANCELAR_EVENTOS:
                cancelarEventos();
                break;

            }
            pausa();
            opcion = menu();
        }
        salir();

    }

    /**
     * Presenta el men�
     * @return  la opci�n seleccionada
     */
    private int menu() {
        borrarPantalla();
        System.out.println("*****************************************");
        System.out.println("   Calendario de eventos ");
        System.out.println("*****************************************");
        System.out.println("1. Mostrar calendario");
        System.out.println("2. Total eventos de mes");
        System.out.println("3. Meses con m�s eventos");
        System.out.println("4. programacion.entregaut6.modelo.Evento m�s largo");
        System.out.println("5. Cancelar eventos");
        System.out.println("6. Salir");
        System.out.print("Teclee opci�n: ");
        int opcion = teclado.nextInt();
        while (!opcionValida(opcion)) {
            System.out.print("Opci�n incorrecta, Teclee opci�n: ");
            opcion = teclado.nextInt();
        }
        teclado.nextLine();
        return opcion;
    }

    /**
     *  Devuelve true si la opci�n es correcta, false en otro caso
     *
     */
    private boolean opcionValida(int opcion) {
        return opcion >= MOSTRAR_CALENDARIO && opcion <= SALIR;
    }

    /**
     * Muestra el calendario de eventos
     */
    private void mostrarCalendario() {
        borrarPantalla();
        System.out.println(calendario);
    }

    /**
     * Muestra el total de eventos en el mes introducido por teclado
     */
    private void totalEventosMes() {
        System.out.print("Introduzca nombre de mes: ");  
        Mes mes = null;
        try {
            mes = Mes.valueOf(teclado.nextLine().trim().toUpperCase());
            int total = calendario.totalEventosEnMes(mes);
            if (total == 0) {
                throw new IllegalArgumentException();
            } else {
                System.out.println("Hay " + total +
                    " eventos en  " + mes.toString());
            }
        }
        catch (IllegalArgumentException e) {
            if (mes == null) {
                System.out.println("No es un nombre correcto de mes");
            }
            else {
                 System.out.println(mes + " no existe en el calendario");
            }
        }

    }

    /**
     *  Muestra los meses con mayor n� de eventos
     */
    private void mesesConMasEventos() {
        System.out.println("programacion.entregaut6.modelo.Mes/es con m�s eventos: "
            + calendario.mesesConMasEventos());
    }

    /**
     * muestra el evento de mayor duraci�n
     */
    private void eventoMasLargo() {
        System.out.println("El evento de mayor duraci�n es "
            + calendario.eventoMasLargo());

    }

    /**
     * Cancela los eventos de un determinado d�a de la semana
     * de los meses indicados  
     */
    private void cancelarEventos() {
        System.out.println("Teclee en una l�nea los nombres de mes"
            + "\nen los que se van cancelar eventos");
        String[] datos = teclado.nextLine().split("\\s+");
        try {
            Mes[] meses = new Mes[datos.length];
            for (int i = 0; i < datos.length; i++) {
                meses[i] = Mes.valueOf(datos[i].toUpperCase());
            }
            System.out.println("Teclee n� d�a de la semana\n " +
                "(1-LUNES, 2-MARTES ...6-SABADO, 7-DOMINGO)");
            int dia = teclado.nextInt();
            while (!diaCorrecto(dia)) {
                System.out.println("Error, Teclee n� d�a de la semana\n" +
                    "(1-LUNES, 2-MARTES ...6-SABADO, 7-DOMINGO)");
                dia = teclado.nextInt();
            }

            int borrados = calendario.cancelarEventos(meses, dia);
            System.out.println("Se han borrado " + borrados + " evento/s");
            teclado.nextLine();
        }
        catch (IllegalArgumentException e) {
            System.out.println("programacion.entregaut6.modelo.Mes incorrecto o no existe en el calendario");
        }

        
    }
    /**
     * Borrar la pantalla
     */
    private boolean diaCorrecto(int dia) {
        return dia >= 1 && dia <= 6;
    }

    /**
     * Borrar la pantalla
     */
    private void borrarPantalla() {
        System.out.println("\u000C");
    }

    /**
     * Hacer una pausa
     */
    private void pausa() {
        System.out.println("Pulse <Intro> para continuar");
        teclado.nextLine();
    }

    /**
     * salir de la aplicaci�n
     */
    private void salir() {
        System.out.println("\n----- Finalizada la aplicaci�n  ------\n");
    }

}
