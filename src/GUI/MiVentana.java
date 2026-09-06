package GUI;

import algoritmos.BuscadorBinario;
import algoritmos.Caracteristica;
import algoritmos.DecisorGreedy;
import entidades.Personaje;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiVentana {
    private JPanel panelPrincipal;
    private JPanel dsfsg;
    private JPanel panelJuego;
    private JPanel panelInicio;
    private JButton jugadorVSMaquinaButton;
    private JButton maquinaVSMaquinaButton;
    private JButton salirButton;
    private JPanel panelEstado;
    private JPanel panelGrilla;
    private JPanel panelAcciones;
    private JButton wonderwomanButton;
    private JButton supergirlButton;
    private JButton batgirlButton;
    private JButton gatubelaButton;
    private JButton harleyQuinnButton;
    private JButton flashButton;
    private JButton brujaEscarlataButton;
    private JButton capitanaMarvelButton;
    private JButton helaButton;
    private JButton tormentaButton;
    private JButton supermanButton;
    private JButton batmanButton;
    private JButton aquamanButton;
    private JButton deadpoolButton;
    private JButton ironManButton;
    private JButton thorButton;
    private JButton spidermanButton;
    private JButton doctorStrangeButton;
    private JButton hulkButton;
    private JButton viudaNegraButton;
    private JButton gamoraButton;
    private JButton spiderGwenButton;
    private JButton shazamButton;
    private JLabel lblTurno;
    private JLabel lblPersonajeSecreto;
    private JButton btnVolverInicio;
    private JButton btnArriesgar;
    private JButton btnPreguntar;

    // Mapa para conectar cada botón visual con su objeto Personaje correspondiente
    private Map<JButton, Personaje> mapaPersonajes;

    private motor.GestorPartida gestorPartida;

    private Personaje personajeSecretoMaquina; // lo que VOS tenes que adivinar
    private Personaje personajeSecretoJugador; // lo que LA MAQUINA tiene que adivinar

    // --- Misma logica que usa la consola (algoritmos/DecisorGreedy) ---
    private DecisorGreedy decisorGreedy;
    // Misma Busqueda Binaria (Divide y Conquista) que usa PartidaHumanoVsMaquina
    // por consola: la GUI resuelve toda seleccion por ID, no por posicion en
    // la lista, para que el algoritmo aplicado sea el mismo en ambos modos.
    private BuscadorBinario buscadorBinario;
    private List<Personaje> vivos; // candidatos del secreto de la maquina, compatibles con tus respuestas
    private List<String> preguntasHechas; // preguntas que VOS ya le hiciste a la maquina
    private List<Personaje> vivosMaquina; // candidatos de TU secreto, compatibles con lo que respondiste
    private List<String> preguntasHechasMaquina; // preguntas que LA MAQUINA ya te hizo
    private int turnoActual;
    private Map<String, String> textoPregunta; // nombre interno de Caracteristica -> texto para mostrar

    public MiVentana() {
        mapaPersonajes = new HashMap<>();
        gestorPartida = new motor.GestorPartida();
        decisorGreedy = new DecisorGreedy();
        buscadorBinario = new BuscadorBinario();

        cargarIconosPersonajes();

        // La máquina elige un personaje secreto aleatorio para esta partida
        Personaje[] listaMotor = gestorPartida.getPersonajes();

        // Mapeamos cada botón con su respectivo personaje del motor según el nombre
        // (Asegurate de que los nombres de los personajes en el array coincidan con los textos de los botones)
        for (Personaje p : listaMotor) {
            switch (p.getNombre()) {
                case "Wonder Woman": mapaPersonajes.put(wonderwomanButton, p); break;
                case "Supergirl": mapaPersonajes.put(supergirlButton, p); break;
                case "Batgirl": mapaPersonajes.put(batgirlButton, p); break;
                case "Gatubela": mapaPersonajes.put(gatubelaButton, p); break;
                case "Harley Quinn": mapaPersonajes.put(harleyQuinnButton, p); break;
                case "Flash": mapaPersonajes.put(flashButton, p); break;
                case "Bruja Escarlata": mapaPersonajes.put(brujaEscarlataButton, p); break;
                case "Capitana Marvel": mapaPersonajes.put(capitanaMarvelButton, p); break;
                case "Viuda Negra": mapaPersonajes.put(viudaNegraButton, p); break;
                case "Gamora": mapaPersonajes.put(gamoraButton, p); break;
                case "Spider-Gwen": mapaPersonajes.put(spiderGwenButton, p); break;
                case "Shazam": mapaPersonajes.put(shazamButton, p); break;
                case "Hela": mapaPersonajes.put(helaButton, p); break;
                case "Tormenta": mapaPersonajes.put(tormentaButton, p); break;
                case "Superman": mapaPersonajes.put(supermanButton, p); break;
                case "Batman": mapaPersonajes.put(batmanButton, p); break;
                case "Aquaman": mapaPersonajes.put(aquamanButton, p); break;
                case "Deadpool": mapaPersonajes.put(deadpoolButton, p); break;
                case "Iron Man": mapaPersonajes.put(ironManButton, p); break;
                case "Thor": mapaPersonajes.put(thorButton, p); break;
                case "Spiderman": mapaPersonajes.put(spidermanButton, p); break;
                case "Doctor Strange": mapaPersonajes.put(doctorStrangeButton, p); break;
                case "Hulk": mapaPersonajes.put(hulkButton, p); break;
            }
        }

        // Texto legible para cada Caracteristica que maneja DecisorGreedy
        // (los nombres internos son los mismos que usa Caracteristica.evaluar()).
        // Cubren los filtros de la consigna: genero, calvicie, lentes y color de pelo.
        textoPregunta = new HashMap<>();
        textoPregunta.put("esMujer", "¿Es mujer?");
        textoPregunta.put("calvo", "¿Es calvo/a?");
        textoPregunta.put("usaLentes", "¿Usa lentes?");
        textoPregunta.put("peloColorado", "¿Tiene el pelo colorado?");
        textoPregunta.put("peloNegro", "¿Tiene el pelo negro?");
        textoPregunta.put("peloAmarillo", "¿Tiene el pelo amarillo?");

        // --- CONFIGURACIÓN DE LAS ETIQUETAS (JLabels) ---

        // 1. Activar el fondo (para que deje de ser transparente)
        lblTurno.setOpaque(true);
        lblPersonajeSecreto.setOpaque(true);

        // 2. Colores: Fondo oscuro y letra clara
        lblTurno.setBackground(new java.awt.Color(139, 0, 0)); // Gris muy oscuro
        lblTurno.setForeground(java.awt.Color.WHITE);

        lblPersonajeSecreto.setBackground(new java.awt.Color(139, 0, 0));
        lblPersonajeSecreto.setForeground(java.awt.Color.WHITE);

        // 3. EL ESPACIADO (El colchón interno: Arriba, Izquierda, Abajo, Derecha)
        lblTurno.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lblPersonajeSecreto.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // --- CONFIGURACIÓN DE LOS BOTONES DE ACCIÓN (PANEL SUR) ---

        // 1. Estética profesional para los botones (Fondo oscuro, texto blanco y sin bordes feos)
        java.awt.Color colorBoton = new java.awt.Color(50, 50, 50);
        java.awt.Color colorTexto = java.awt.Color.WHITE;
        java.awt.Font fuenteBoton = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);

        JButton[] botonesAccion = {btnPreguntar, btnArriesgar, btnVolverInicio};
        for (JButton b : botonesAccion) {
            if (b != null) {
                b.setBackground(colorBoton);
                b.setForeground(colorTexto);
                b.setFont(fuenteBoton);
                b.setFocusPainted(false);
                // Le damos un poco de "respiración" al botón para que no esté apretado
                b.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        }

        // Arranca una partida Jugador vs Maquina, simetrica como en consola
        // (la maquina tambien te pregunta a vos)
        jugadorVSMaquinaButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                iniciarPartidaHumanoVsMaquina();
            }
        });

        // Corre una partida Maquina vs Maquina reusando PartidaMaquinaVsMaquina
        // tal cual, y muestra su log en un dialogo
        maquinaVSMaquinaButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                iniciarPartidaMaquinaVsMaquina();
            }
        });

        // Acción para salir
        salirButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.exit(0);
            }
        });


        // 2. Acción del botón Preguntar
        if (btnPreguntar != null) {
            btnPreguntar.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // Mismas caracteristicas todavia no preguntadas que usa la version de consola
                    List<Caracteristica> disponibles = decisorGreedy.obtenerDisponibles(preguntasHechas);

                    if (disponibles.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                                "¡Ya no quedan preguntas disponibles!",
                                "Aviso",
                                javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Convertimos cada Caracteristica en su texto legible para el ComboBox
                    String[] arrayPreguntas = new String[disponibles.size()];
                    for (int i = 0; i < disponibles.size(); i++) {
                        arrayPreguntas[i] = textoPregunta.get(disponibles.get(i).getNombre());
                    }

                    javax.swing.JComboBox<String> comboPreguntas = new javax.swing.JComboBox<>(arrayPreguntas);

                    // Mostramos un panel flotante (JOptionPane) con el ComboBox adentro
                    int resultado = javax.swing.JOptionPane.showConfirmDialog(
                            null,
                            comboPreguntas,
                            "Seleccioná una pregunta para hacer:",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION,
                            javax.swing.JOptionPane.QUESTION_MESSAGE
                    );

                    // Si el usuario apretó OK y eligió una pregunta
                    if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                        Caracteristica preguntaElegida = disponibles.get(comboPreguntas.getSelectedIndex());
                        String preguntaSeleccionada = textoPregunta.get(preguntaElegida.getNombre());

                        // LA MÁQUINA RESPONDE SOLA: se reusa Caracteristica.evaluar(), igual que en consola
                        boolean respuestaSi = preguntaElegida.evaluar(personajeSecretoMaquina);

                        preguntasHechas.add(preguntaElegida.getNombre());

                        String textoRespuesta = respuestaSi ? "¡SÍ!" : "¡NO!";
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "Pregunta: " + preguntaSeleccionada + "\n\nRespuesta de la Máquina: " + textoRespuesta,
                                "Respuesta del Servidor",
                                javax.swing.JOptionPane.INFORMATION_MESSAGE
                        );

                        // Se reduce el grupo de candidatos con el mismo algoritmo (Divide y Conquista/particion)
                        // que usa PartidaHumanoVsMaquina
                        vivos = decisorGreedy.reducirGrupo(vivos, preguntaElegida, respuestaSi);

                        // FILTRAR LA GRILLA: se descartan visualmente los personajes que ya no son candidatos
                        for (Map.Entry<JButton, Personaje> entry : mapaPersonajes.entrySet()) {
                            JButton boton = entry.getKey();
                            Personaje p = entry.getValue();

                            if (boton.isEnabled() && !vivos.contains(p)) {
                                descartarPersonajeVisualmente(boton);
                            }
                        }

                        // Termino tu turno: ahora le toca preguntar a la maquina
                        turnoActual++;
                        turnoDeLaMaquina();
                    }
                }
            });
        }

        // 3. Acción del botón Arriesgar
        if (btnArriesgar != null) {
            btnArriesgar.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // Usamos el mismo grupo de candidatos que mantiene el algoritmo Greedy
                    // (vivos), en vez de volver a inspeccionar los botones de la grilla.
                    if (vivos.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                                "¡No quedan personajes disponibles para arriesgar!",
                                "Error",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Igual que arriba: se muestra "ID - Nombre" pero la
                    // resolucion final es por ID via BuscadorBinario, no por
                    // posicion en el combo.
                    String[] opcionesVivos = new String[vivos.size()];
                    for (int i = 0; i < vivos.size(); i++) {
                        opcionesVivos[i] = String.format("%02d - %s", vivos.get(i).getId(), vivos.get(i).getNombre());
                    }

                    javax.swing.JComboBox<String> comboArriesgar = new javax.swing.JComboBox<>(opcionesVivos);

                    int resultado = javax.swing.JOptionPane.showConfirmDialog(
                            null,
                            comboArriesgar,
                            "¿Quién es tu personaje secreto?",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION,
                            javax.swing.JOptionPane.QUESTION_MESSAGE
                    );

                    if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                        int idArriesgado = extraerId((String) comboArriesgar.getSelectedItem());
                        Personaje personajeElegido = buscadorBinario.buscarPorId(gestorPartida.getPersonajes(), idArriesgado);

                        if (personajeElegido == personajeSecretoMaquina) {
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "¡Felicidades! Adivinaste el personaje secreto: " + personajeElegido.getNombre(),
                                    "¡Victoria!",
                                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "¡Fallaste! El personaje secreto era: " + personajeSecretoMaquina.getNombre(),
                                    "Game Over",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                        // Arriesgar siempre termina la partida, aciertes o no (igual que en consola)
                        volverAlInicio();
                    }
                }
            });
        }

        // 4. Acción del botón Volver al Menú
        if (btnVolverInicio != null) {
            btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    volverAlInicio();
                }
            });
        }
    }

    // Extrae el ID numerico de una opcion de combo con formato "ID - Nombre".
    private int extraerId(String opcion) {
        return Integer.parseInt(opcion.split(" - ")[0].trim());
    }

    // Carga (o recarga) el icono a color y el texto de los 22 botones de personaje
    private void cargarIconosPersonajes() {
        configurarBotonPersonaje(wonderwomanButton, "wonderwoman.png", "Wonder Woman");
        configurarBotonPersonaje(supergirlButton, "supergirl.png", "Supergirl");
        configurarBotonPersonaje(batgirlButton, "batgirl.png", "Batgirl");
        configurarBotonPersonaje(gatubelaButton, "gatubela.png", "Gatúbela");
        configurarBotonPersonaje(harleyQuinnButton, "harleyquinn.png", "Harley Quinn");
        configurarBotonPersonaje(flashButton, "flash.png", "Flash");
        configurarBotonPersonaje(brujaEscarlataButton, "brujaescarlata.png", "Bruja Escarlata");
        configurarBotonPersonaje(capitanaMarvelButton, "capitanamarvel.png", "Capitana Marvel");
        configurarBotonPersonaje(viudaNegraButton, "viudanegra.png", "Viuda Negra");
        configurarBotonPersonaje(gamoraButton, "gamora.png", "Gamora");
        configurarBotonPersonaje(spiderGwenButton, "spidergwen.png", "Spider-Gwen");
        configurarBotonPersonaje(shazamButton, "shazam.png", "Shazam");
        configurarBotonPersonaje(helaButton, "hela.png", "Hela");
        configurarBotonPersonaje(tormentaButton, "tormenta.png", "Tormenta");
        configurarBotonPersonaje(supermanButton, "superman.png", "Superman");
        configurarBotonPersonaje(batmanButton, "batman.png", "Batman");
        configurarBotonPersonaje(aquamanButton, "aquaman.png", "Aquaman");
        configurarBotonPersonaje(deadpoolButton, "deadpool.png", "Deadpool");
        configurarBotonPersonaje(ironManButton, "ironman.png", "Iron Man");
        configurarBotonPersonaje(thorButton, "thor.png", "Thor");
        configurarBotonPersonaje(spidermanButton, "spiderman.png", "Spider-Man");
        configurarBotonPersonaje(doctorStrangeButton, "doctorstrange.png", "Dr. Strange");
        configurarBotonPersonaje(hulkButton, "hulk.png", "Hulk");
    }

    // Vuelve a dejar la grilla como al principio de una partida (todos habilitados, a color)
    private void resetearGrilla() {
        cargarIconosPersonajes();
        for (JButton boton : mapaPersonajes.keySet()) {
            boton.setEnabled(true);
        }
    }

    private void volverAlInicio() {
        java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
        cl.show(panelPrincipal, "PanelInicio");
    }

    // Arranca una partida Jugador vs Maquina simetrica: vos elegis tu secreto,
    // la maquina elige el suyo, y en cada ronda primero pregunta la maquina
    // (turnoDeLaMaquina) y despues elegis vos Preguntar o Arriesgar.
    private void iniciarPartidaHumanoVsMaquina() {
        Personaje[] listaMotor = gestorPartida.getPersonajes();

        resetearGrilla();

        // --- Lo que VOS tenes que adivinar ---
        personajeSecretoMaquina = listaMotor[(int) (Math.random() * listaMotor.length)];
        System.out.println("[DEBUG] El personaje secreto de la máquina es: " + personajeSecretoMaquina.getNombre());
        vivos = new ArrayList<>(Arrays.asList(listaMotor));
        preguntasHechas = new ArrayList<>();

        // --- Elegis tu secreto: lo que la maquina tiene que adivinar ---
        // El combo muestra "ID - Nombre" pero la seleccion se resuelve por ID
        // con BuscadorBinario (Divide y Conquista) sobre el array ya ordenado
        // por ID, tal como pide la consigna, no por posicion en la lista.
        String[] opcionesSecreto = new String[listaMotor.length];
        for (int i = 0; i < listaMotor.length; i++) {
            opcionesSecreto[i] = String.format("%02d - %s", listaMotor[i].getId(), listaMotor[i].getNombre());
        }
        javax.swing.JComboBox<String> comboSecreto = new javax.swing.JComboBox<>(opcionesSecreto);
        javax.swing.JOptionPane.showMessageDialog(null, comboSecreto,
                "Elegí tu personaje secreto (la máquina va a tratar de adivinarlo)",
                javax.swing.JOptionPane.PLAIN_MESSAGE);
        int idElegido = extraerId((String) comboSecreto.getSelectedItem());
        personajeSecretoJugador = buscadorBinario.buscarPorId(listaMotor, idElegido);

        vivosMaquina = new ArrayList<>(Arrays.asList(listaMotor));
        preguntasHechasMaquina = new ArrayList<>();
        turnoActual = 1;

        java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
        cl.show(panelPrincipal, "PanelJuego");

        lblPersonajeSecreto.setText("Tu personaje secreto: " + personajeSecretoJugador.getNombre());

        turnoDeLaMaquina();
    }

    // La maquina te hace una pregunta (o arriesga si ya no le hace falta seguir
    // preguntando), usando el mismo DecisorGreedy que la version de consola.
    private void turnoDeLaMaquina() {
        lblTurno.setText("Turno " + turnoActual + ": pregunta la máquina...");

        if (vivosMaquina.size() == 1) {
            Personaje adivinado = vivosMaquina.get(0);
            mostrarResultadoFinal("La máquina arriesga que tu personaje es " + adivinado.getNombre() + ".",
                    adivinado == personajeSecretoJugador, "la máquina");
            return;
        }

        Caracteristica pregunta = decisorGreedy.elegirMejorPregunta(vivosMaquina, preguntasHechasMaquina);

        if (pregunta == null) {
            // Candidatos "gemelos" en las caracteristicas usadas: la maquina
            // arriesga al azar entre los que quedan, igual que en consola.
            Personaje alAzar = vivosMaquina.get((int) (Math.random() * vivosMaquina.size()));
            mostrarResultadoFinal("La máquina no tiene más preguntas útiles y arriesga: " + alAzar.getNombre() + ".",
                    alAzar == personajeSecretoJugador, "la máquina");
            return;
        }

        boolean respuesta = pregunta.evaluar(personajeSecretoJugador);
        preguntasHechasMaquina.add(pregunta.getNombre());
        vivosMaquina = decisorGreedy.reducirGrupo(vivosMaquina, pregunta, respuesta);

        javax.swing.JOptionPane.showMessageDialog(null,
                "La máquina pregunta: " + textoPregunta.get(pregunta.getNombre())
                        + "\n\nTu personaje (" + personajeSecretoJugador.getNombre() + ") responde: "
                        + (respuesta ? "¡SÍ!" : "¡NO!"),
                "La máquina te pregunta", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        lblTurno.setText("Turno " + turnoActual + ": tu turno (Preguntar o Arriesgar)");
    }

    // Muestra el resultado de un "arriesgo" de la maquina y termina la partida,
    // aciertes o no (igual que en consola: arriesgar siempre es decisivo).
    private void mostrarResultadoFinal(String descripcionJugada, boolean acerto, String quienArriesgo) {
        String mensaje = descripcionJugada + (acerto
                ? "\n\n¡Acertó! Gana " + quienArriesgo + "."
                : "\n\nSe equivocó. ¡Ganaste vos!");
        javax.swing.JOptionPane.showMessageDialog(null, mensaje, "Fin de la partida",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        volverAlInicio();
    }

    // Corre una partida Maquina vs Maquina reusando PartidaMaquinaVsMaquina tal
    // cual la usa la consola, capturando su salida para mostrarla pantalla a
    // pantalla (un turno por vez) en vez de tirar todo el log junto.
    private void iniciarPartidaMaquinaVsMaquina() {
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        java.io.PrintStream capturado = new java.io.PrintStream(buffer, true, java.nio.charset.StandardCharsets.UTF_8);
        java.io.PrintStream original = System.out;
        System.setOut(capturado);
        try {
            new motor.maquinaVsMaquina.PartidaMaquinaVsMaquina(gestorPartida.getPersonajes()).jugar();
        } finally {
            System.setOut(original);
        }

        String logCompleto = buffer.toString(java.nio.charset.StandardCharsets.UTF_8);
        String delimitador = "========== TURNO";
        // Partimos el log ya generado en un pedazo por turno (mas la intro),
        // conservando el delimitador al principio de cada pedazo
        String[] turnos = logCompleto.split("(?=" + java.util.regex.Pattern.quote(delimitador) + ")");

        mostrarLogPaginado(turnos);
    }

    // Muestra un log de texto de a un pedazo por vez con un boton "Turno
    // siguiente", para poder seguir la partida Maquina vs Maquina paso a paso.
    private void mostrarLogPaginado(String[] pedazos) {
        JTextArea areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setPreferredSize(new java.awt.Dimension(700, 500));

        JButton btnSiguiente = new JButton("Turno siguiente >>");
        int[] indice = {0};

        JDialog dialogo = new JDialog((java.awt.Frame) null, "Máquina vs Máquina", true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setLayout(new java.awt.BorderLayout(10, 10));
        dialogo.add(scroll, java.awt.BorderLayout.CENTER);
        dialogo.add(btnSiguiente, java.awt.BorderLayout.SOUTH);

        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (indice[0] < pedazos.length) {
                    areaLog.append(pedazos[indice[0]]);
                    areaLog.setCaretPosition(areaLog.getDocument().getLength());
                    indice[0]++;
                    if (indice[0] >= pedazos.length) {
                        btnSiguiente.setText("Cerrar");
                    }
                } else {
                    dialogo.dispose();
                }
            }
        });

        // Mostramos la intro (antes del primer "TURNO") apenas se abre el dialogo
        if (indice[0] < pedazos.length) {
            areaLog.append(pedazos[indice[0]]);
            indice[0]++;
        }

        dialogo.pack();
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }

    private void configurarBotonPersonaje(javax.swing.JButton boton, String nombreArchivo, String nombreVisible) {
        if (boton == null) return;

        // 1. Configuración del texto (¡Acá está la magia!)
        boton.setText(nombreVisible);
        boton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM); // Manda el texto abajo de la foto
        boton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER); // Centra el texto

        // Opcional: Podés cambiarle la fuente y el tamaño para que se lea mejor
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));

        // 2. Limpieza estética
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);

        // 3. Carga y escalado de la imagen
        java.net.URL ruta = getClass().getResource("/GUI/resources/personajes/" + nombreArchivo);
        if (ruta != null) {
            javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(ruta);
            java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
            boton.setIcon(new javax.swing.ImageIcon(imagenEscalada));
        } else {
            System.out.println("Ojo: No se encontró la imagen -> " + nombreArchivo);
        }
    }

    // Método para deshabilitar gráficamente a un personaje descartado (escala de grises)
    private void descartarPersonajeVisualmente(JButton boton) {
        if (boton == null) return;

        boton.setEnabled(false);

        javax.swing.Icon icono = boton.getIcon();
        if (icono instanceof javax.swing.ImageIcon) {
            java.awt.Image imgOriginal = ((javax.swing.ImageIcon) icono).getImage();

            java.awt.image.BufferedImage imgGrises = new java.awt.image.BufferedImage(
                    imgOriginal.getWidth(null), imgOriginal.getHeight(null),
                    java.awt.image.BufferedImage.TYPE_INT_ARGB
            );
            java.awt.Graphics2D g2d = imgGrises.createGraphics();
            g2d.drawImage(imgOriginal, 0, 0, null);

            java.awt.image.ColorConvertOp op = new java.awt.image.ColorConvertOp(
                    java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_GRAY), null
            );
            op.filter(imgGrises, imgGrises);
            g2d.dispose();

            boton.setIcon(new javax.swing.ImageIcon(imgGrises));
        }
    }

    private void createUIComponents() {
        // --- 0. INICIALIZAR CONTENEDOR PRINCIPAL ---
        panelPrincipal = new JPanel();

        // --- 1. CARGAR IMÁGENES ---
        java.net.URL rutaInicio = getClass().getResource("/GUI/resources/fondo_inicio.png");
        java.awt.Image imgInicio = (rutaInicio != null) ? new javax.swing.ImageIcon(rutaInicio).getImage() : null;

        java.net.URL rutaJuego = getClass().getResource("/GUI/resources/fondo_juego.png");
        java.awt.Image imgJuego = (rutaJuego != null) ? new javax.swing.ImageIcon(rutaJuego).getImage() : null;

        // --- 2. DIBUJAR CARTA 1 (INICIO) ---
        panelInicio = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (imgInicio != null) g.drawImage(imgInicio, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // --- 3. DIBUJAR CARTA 2 (JUEGO) ---
        panelJuego = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (imgJuego != null) g.drawImage(imgJuego, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    // Agregá esto al final de MiVentana.java
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}