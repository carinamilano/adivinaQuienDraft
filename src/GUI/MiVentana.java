package GUI;

import entidades.Personaje;
import javax.swing.*;
import java.util.HashMap;
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
    private java.util.ArrayList<String> preguntasDisponibles;

    // Mapa para conectar cada botón visual con su objeto Personaje correspondiente
    private Map<JButton, Personaje> mapaPersonajes;

    private motor.GestorPartida gestorPartida;
    private Personaje personajeSecretoPartida;

    private Personaje personajeSecretoMaquina;

    public MiVentana() {
        // Inicializamos el mapa de personajes
        mapaPersonajes = new HashMap<>();

        // Fila 1
        configurarBotonPersonaje(wonderwomanButton, "wonderwoman.png", "Wonder Woman");
        configurarBotonPersonaje(supergirlButton, "supergirl.png", "Supergirl");
        configurarBotonPersonaje(batgirlButton, "batgirl.png", "Batgirl");
        configurarBotonPersonaje(gatubelaButton, "gatubela.png", "Gatúbela");
        configurarBotonPersonaje(harleyQuinnButton, "harleyquinn.png", "Harley Quinn");
        configurarBotonPersonaje(flashButton, "flash.png", "Flash");

        // Fila 2
        configurarBotonPersonaje(brujaEscarlataButton, "brujaescarlata.png", "Bruja Escarlata");
        configurarBotonPersonaje(capitanaMarvelButton, "capitanamarvel.png", "Capitana Marvel");
        configurarBotonPersonaje(viudaNegraButton, "viudanegra.png", "Viuda Negra");
        configurarBotonPersonaje(gamoraButton, "gamora.png", "Gamora");
        configurarBotonPersonaje(spiderGwenButton, "spidergwen.png", "Spider-Gwen");
        configurarBotonPersonaje(shazamButton, "shazam.png", "Shazam");

        // Fila 3
        configurarBotonPersonaje(helaButton, "hela.png", "Hela");
        configurarBotonPersonaje(tormentaButton, "tormenta.png", "Tormenta");
        configurarBotonPersonaje(supermanButton, "superman.png", "Superman");
        configurarBotonPersonaje(batmanButton, "batman.png", "Batman");
        configurarBotonPersonaje(aquamanButton, "aquaman.png", "Aquaman");
        configurarBotonPersonaje(deadpoolButton, "deadpool.png", "Deadpool");

        // Fila 4
        configurarBotonPersonaje(ironManButton, "ironman.png", "Iron Man");
        configurarBotonPersonaje(thorButton, "thor.png", "Thor");
        configurarBotonPersonaje(spidermanButton, "spiderman.png", "Spider-Man");
        configurarBotonPersonaje(doctorStrangeButton, "doctorstrange.png", "Dr. Strange");
        configurarBotonPersonaje(hulkButton, "hulk.png", "Hulk");

        gestorPartida = new motor.GestorPartida();

        // Inicializamos el mapa de personajes
        mapaPersonajes = new HashMap<>();
        gestorPartida = new motor.GestorPartida();

        // La máquina elige un personaje secreto aleatorio para esta partida
        Personaje[] listaMotor = gestorPartida.getPersonajes();
        int indiceAleatorio = (int) (Math.random() * listaMotor.length);
        personajeSecretoMaquina = listaMotor[indiceAleatorio];

        // (Para pruebas: podés ver en la consola de IntelliJ qué personaje eligió la máquina)
        System.out.println("[DEBUG] El personaje secreto de la máquina es: " + personajeSecretoMaquina.getNombre());


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




        /**
        // Asociamos cada botón con su respectiva entidad Personaje para poder filtrarlos
        // (ID, Genero, Nombre, vuela, usaCapa, esDC, esMujer, tienePoderesMagicos, usaMascara, tieneSuperFuerza, esHumano)
        mapaPersonajes.put(wonderwomanButton, new Personaje(1, "Femenino", "Wonder Woman", true, false, true, true, true, false, true, false));
        mapaPersonajes.put(supergirlButton, new Personaje(2, "Femenino", "Supergirl", true, true, true, true, false, false, true, false));
        mapaPersonajes.put(batgirlButton, new Personaje(3, "Femenino", "Batgirl", false, true, true, true, false, true, false, true));
        mapaPersonajes.put(gatubelaButton, new Personaje(4, "Femenino", "Gatúbela", false, false, true, true, false, true, false, true));
        mapaPersonajes.put(harleyQuinnButton, new Personaje(5, "Femenino", "Harley Quinn", false, false, true, true, false, false, false, true));
        mapaPersonajes.put(flashButton, new Personaje(6, "Masculino", "Flash", false, false, true, false, false, true, true, true));
        mapaPersonajes.put(brujaEscarlataButton, new Personaje(7, "Femenino", "Bruja Escarlata", true, false, false, true, true, false, false, true));
        mapaPersonajes.put(capitanaMarvelButton, new Personaje(8, "Femenino", "Capitana Marvel", true, false, false, true, false, false, true, false));
        mapaPersonajes.put(viudaNegraButton, new Personaje(9, "Femenino", "Viuda Negra", false, false, false, true, false, false, false, true));
        mapaPersonajes.put(gamoraButton, new Personaje(10, "Femenino", "Gamora", false, false, false, true, false, false, true, false));
        mapaPersonajes.put(spiderGwenButton, new Personaje(11, "Femenino", "Spider-Gwen", false, false, false, true, false, true, true, true));
        mapaPersonajes.put(shazamButton, new Personaje(12, "Masculino", "Shazam", true, true, true, false, true, false, true, true));
        mapaPersonajes.put(helaButton, new Personaje(13, "Femenino", "Hela", true, false, false, true, true, false, true, false));
        mapaPersonajes.put(tormentaButton, new Personaje(14, "Femenino", "Tormenta", true, true, false, true, true, false, false, true));
        mapaPersonajes.put(supermanButton, new Personaje(15, "Masculino", "Superman", true, true, true, false, false, false, true, false));
        mapaPersonajes.put(batmanButton, new Personaje(16, "Masculino", "Batman", false, true, true, false, false, true, false, true));
        mapaPersonajes.put(aquamanButton, new Personaje(17, "Masculino", "Aquaman", false, false, true, false, false, false, true, false));
        mapaPersonajes.put(deadpoolButton, new Personaje(18, "Masculino", "Deadpool", false, false, false, false, false, true, true, true));
        mapaPersonajes.put(ironManButton, new Personaje(19, "Masculino", "Iron Man", true, false, false, false, false, false, false, true));
        mapaPersonajes.put(thorButton, new Personaje(20, "Masculino", "Thor", true, true, false, false, true, false, true, false));
        mapaPersonajes.put(spidermanButton, new Personaje(21, "Masculino", "Spider-Man", false, false, false, false, false, true, true, true));
        mapaPersonajes.put(doctorStrangeButton, new Personaje(22, "Masculino", "Dr. Strange", true, false, false, false, true, false, false, true));
        mapaPersonajes.put(hulkButton, new Personaje(23, "Masculino", "Hulk", false, false, false, false, false, false, true, false));
        **/

        // Inicializamos las preguntas disponibles basadas exactamente en los atributos de la clase Personaje
        preguntasDisponibles = new java.util.ArrayList<>();
        preguntasDisponibles.add("¿El personaje vuela?");
        preguntasDisponibles.add("¿Usa capa?");
        preguntasDisponibles.add("¿Pertenece al universo de DC?"); // (esDC)
        preguntasDisponibles.add("¿Tiene poderes mágicos?");
        preguntasDisponibles.add("¿Usa máscara?");
        preguntasDisponibles.add("¿Tiene superfuerza?");
        preguntasDisponibles.add("¿Es humano?");

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

        // Acción para cambiar de pantalla al tocar el botón
        jugadorVSMaquinaButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Le pedimos al CardLayout que muestre la carta llamada "PanelJuego"
                java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
                cl.show(panelPrincipal, "PanelJuego");
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
                    // Validamos si quedan preguntas en la lista
                    if (preguntasDisponibles.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                                "¡Ya no quedan preguntas disponibles!",
                                "Aviso",
                                javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Convertimos el ArrayList en un array compatible con el JComboBox
                    String[] arrayPreguntas = preguntasDisponibles.toArray(new String[0]);

                    // Creamos el ComboBox con las preguntas vivas
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
                        String preguntaSeleccionada = (String) comboPreguntas.getSelectedItem();

                        // 1. La eliminamos de la lista para que no vuelva a aparecer
                        preguntasDisponibles.remove(preguntaSeleccionada);

                        // 2. LA MÁQUINA RESPONDE SOLA: Evaluamos la pregunta contra su personaje secreto
                        boolean respuestaSi = evaluarPregunta(personajeSecretoMaquina, preguntaSeleccionada);

                        // 3. Mostramos la respuesta real de la máquina en pantalla
                        String textoRespuesta = respuestaSi ? "¡SÍ!" : "¡NO!";
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "Pregunta: " + preguntaSeleccionada + "\n\nRespuesta de la Máquina: " + textoRespuesta,
                                "Respuesta del Servidor",
                                javax.swing.JOptionPane.INFORMATION_MESSAGE
                        );

                        // 4. FILTRAR LA GRILLA: Recorremos los botones y descartamos los que no coincidan
                        for (Map.Entry<JButton, Personaje> entry : mapaPersonajes.entrySet()) {
                            JButton boton = entry.getKey();
                            Personaje p = entry.getValue();

                            boolean cumpleCondicion = evaluarPregunta(p, preguntaSeleccionada);

                            // Si la respuesta de la máquina fue SÍ y el personaje NO la cumple (o viceversa), se descarta
                            if (cumpleCondicion != respuestaSi) {
                                descartarPersonajeVisualmente(boton);
                            }
                        }
                    }
                }
            });
        }

        // 3. Acción del botón Arriesgar
        if (btnArriesgar != null) {
            btnArriesgar.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // 1. Recopilamos únicamente los personajes que siguen habilitados en la grilla
                    java.util.ArrayList<Personaje> personajesVivos = new java.util.ArrayList<>();
                    for (Map.Entry<JButton, Personaje> entry : mapaPersonajes.entrySet()) {
                        JButton boton = entry.getKey();
                        if (boton.isEnabled()) { // Si el botón sigue activo, el personaje sigue en juego
                            personajesVivos.add(entry.getValue());
                        }
                    }

                    // Validamos si quedan candidatos
                    if (personajesVivos.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null,
                                "¡No quedan personajes disponibles para arriesgar!",
                                "Error",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 2. Creamos un array con los nombres de los personajes vivos para el ComboBox
                    String[] nombresVivos = new String[personajesVivos.size()];
                    for (int i = 0; i < personajesVivos.size(); i++) {
                        nombresVivos[i] = personajesVivos.get(i).getNombre();
                    }

                    javax.swing.JComboBox<String> comboArriesgar = new javax.swing.JComboBox<>(nombresVivos);

                    // 3. Mostramos la ventana emergente para que elija su candidato
                    int resultado = javax.swing.JOptionPane.showConfirmDialog(
                            null,
                            comboArriesgar,
                            "¿Quién es tu personaje secreto?",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION,
                            javax.swing.JOptionPane.QUESTION_MESSAGE
                    );

                    if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                        String personajeElegido = (String) comboArriesgar.getSelectedItem();

                        // CORREGIDO: Usamos personajeSecretoMaquina que es la que está inicializada
                        if (personajeElegido.equalsIgnoreCase(personajeSecretoMaquina.getNombre())) {
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "¡Felicidades! Adivinaste el personaje secreto: " + personajeElegido,
                                    "¡Victoria!",
                                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

                            // Devolver al menú al ganar
                            java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
                            cl.show(panelPrincipal, "PanelInicio");
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null,
                                    "¡Fallaste! El personaje secreto era: " + personajeSecretoMaquina.getNombre(),
                                    "Game Over",
                                    javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        // 4. Acción del botón Volver al Menú
        if (btnVolverInicio != null) {
            btnVolverInicio.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    java.awt.CardLayout cl = (java.awt.CardLayout) panelPrincipal.getLayout();
                    cl.show(panelPrincipal, "PanelInicio"); // <-- Cambiado a "PanelInicio"
                }
            });
        }
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

    // Método auxiliar que evalúa el atributo booleano de la clase Personaje según la pregunta
    private boolean evaluarPregunta(Personaje p, String pregunta) {
        switch (pregunta) {
            case "¿El personaje vuela?": return p.isVuela();
            case "¿Usa capa?": return p.isUsaCapa();
            case "¿Pertenece al universo de DC?": return p.isEsDC();
            case "¿Tiene poderes mágicos?": return p.isTienePoderesMagicos();
            case "¿Usa máscara?": return p.isUsaMascara();
            case "¿Tiene superfuerza?": return p.isTieneSuperFuerza();
            case "¿Es humano?": return p.isEsHumano();
            default: return false;
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