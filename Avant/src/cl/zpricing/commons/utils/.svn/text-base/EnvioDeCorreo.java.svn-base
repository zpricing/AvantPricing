package cl.zpricing.commons.utils;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EnvioDeCorreo {
	public static int EXITO = 0;
    public static int FALLOENELENVIO = 1;
    public static int FROMVACIO = 2;
    public static int FROMILEGAL = 3;
    public static int TOVACIO = 4;
    public static int SUBJECTVACIO = 5;
    public static int CUERPOVACIO = 6;
    
    private static Logger logger = (Logger) Logger.getLogger(EnvioDeCorreo.class);
    
    public static int normal(String fromNombre, String fromDireccion, String to, String subject, String replyTo, String cc, String bcc, String cuerpo, String signature) {
    	return normal(fromNombre, fromDireccion, to, subject, replyTo, cc, bcc, cuerpo, signature, null);
    }
    
    public static int normal(String fromNombre, String fromDireccion, String to, String subject, String replyTo, String cc, String bcc, String cuerpo, String signature, List<File> attachments) {
        String arregloTo[] = {
            to
        };
        String arregloReplyTo[] = {
            replyTo
        };
        String arregloCc[] = {
            cc
        };
        String arregloBcc[] = {
            bcc
        };
        return enviaCorreo(fromNombre, fromDireccion, arregloTo, subject, arregloReplyTo, arregloCc, arregloBcc, "", "", "", null, "", null, cuerpo, signature, attachments);
    }
/*
    public static int completo(String fromNombre, String fromDireccion, String to[], String subject, String replyTo[], String cc[], String bcc[], String inReplyTo, 
            String references, String comments, String keywords[], String priority, Hashtable camposAdicionales, String cuerpo, String signature)
    {
        return enviaCorreo(fromNombre, fromDireccion, to, subject, replyTo, cc, bcc, inReplyTo, references, comments, keywords, priority, camposAdicionales, cuerpo, signature);
    }
*/
    
    private static int enviaCorreo(String fromNombre, String fromDireccion, String to[], String subject, String replyTo[], String cc[], String bcc[], String inReplyTo, String references, String comments, String keywords[], String priority, Hashtable camposAdicionales, String cuerpo, String signature, List<File> attachments) {
        logger.debug("----------- datos de entrada -----------");
        logger.debug("fromNombre=<" + fromNombre + ">  fromDireccion=<" + fromDireccion + ">");
        logger.debug("subject=<" + subject + ">");
        logger.debug("inReplyTo=<" + inReplyTo + ">  references=<" + references + ">  comments=<" + comments + ">");
        logger.debug("priority=<" + priority + ">");
        logger.debug("camposAdicionales=" + (camposAdicionales != null ? camposAdicionales.toString() : "null"));
        logger.debug("cuerpo:");
        logger.debug("------------------------------");
        logger.debug("" + cuerpo);
        logger.debug("------------------------------");
        logger.debug("signature: <" + signature + ">");
        logger.debug("------------------------------");
        boolean estoyEnProduccion = true;
        if(fromNombre == null || fromNombre.trim().equals("")) {
            fromNombre = "";
        }
        fromNombre = fromNombre.trim();
        if(fromDireccion == null || fromDireccion.trim().equals("")) {
            return FROMVACIO;
        }
        InternetAddress direccionFrom = null;
        try {
            direccionFrom = new InternetAddress(fromDireccion);
            if(!fromNombre.equals("")) {
                direccionFrom.setPersonal(fromNombre);
            }
        }
        catch(Exception ex) {
            logger.fatal("La direcci\363n de origen [" + (fromNombre.equals("") ? fromDireccion : fromNombre + " <" + fromDireccion + ">") + "] es ilegal. El mail ser\341 descartado.");
            return FROMILEGAL;
        }
        String direccionesTo[] = filtraDirecciones(to);
        if(direccionesTo == null) {
            return TOVACIO;
        }
        InternetAddress direccionesInternetTo[] = direccionesInternet(direccionesTo);
        if(direccionesInternetTo == null) {
            return TOVACIO;
        }
        if(subject == null || subject.trim().equals("")) {
            return SUBJECTVACIO;
        }
        String direccionesReplyTo[] = filtraDirecciones(replyTo);
        String direccionesCc[] = filtraDirecciones(cc);
        String direccionesBcc[] = filtraDirecciones(bcc);
        InternetAddress direccionesInternetCc[] = direccionesInternet(direccionesCc);
        InternetAddress direccionesInternetBcc[] = direccionesInternet(direccionesBcc);
        if(inReplyTo == null || inReplyTo.trim().equals("")) {
            inReplyTo = "";
        }
        if(references == null || references.trim().equals("")) {
            references = "";
        }
        if(comments == null || comments.trim().equals("")) {
            comments = "";
        }
        String palabrasClave = StringUtils.listaSeparadaPorComas(keywords);
        if(priority == null || !priority.equals("normal") && !priority.equals("non-urgent") && !priority.equals("urgent")) {
            priority = "";
        }
        Vector camposAdicionalesFormateados = new Vector();
        if(camposAdicionales != null) {
            for(Enumeration e = camposAdicionales.keys(); e.hasMoreElements();) {
                Object o = e.nextElement();
                String s = o.toString().trim();
                String t = camposAdicionales.get(o).toString().trim();
                if(!s.equals("")) {
                    camposAdicionalesFormateados.add("X-" + s + ": " + t);
                }
            }

        }
        if(cuerpo == null || cuerpo.trim().equals("")) {
            return CUERPOVACIO;
        }
        if(signature == null || signature.trim().equals("")) {
            signature = "";
        }
        if(signature.length() > 0 && signature.substring(signature.length() - 1).equals(StringUtils.EOL)) {
            signature = signature.substring(0, signature.length() - 1);
        }
        if(signature.indexOf(StringUtils.EOLchar) == -1) {
            //signature = TextosUtil.parrafo(signature, 72);
        } else {
            signature = signature + StringUtils.EOL;
        }
        
        if(estoyEnProduccion) {
            try {
        		Properties propiedades = TableManager.getInstance().get("correo.properties");
        		
        		String host = propiedades.getProperty("mail.smtp.host");
        		String username = propiedades.getProperty("mail.smtp.user");
        		String pass = propiedades.getProperty("mail.smtp.password");
        		String starttls = propiedades.getProperty("mail.smtp.starttls.enable");
        		String protocol = propiedades.getProperty("mail.transport.protocol");
        		String port = propiedades.getProperty("mail.smtp.port");
        		
        		logger.debug("host : [" + host + "]");
        		logger.debug("username : [" + username + "]");
        		logger.debug("pass : [" + pass + "]");
        		logger.debug("starttls : [" + starttls + "]");
        		logger.debug("protocol : [" + protocol + "]");
        		logger.debug("port : [" + port + "]");
        		
        		Authenticator auth = new SMTPAuthenticator(username, pass);
                Session mailSession = Session.getDefaultInstance(propiedades, auth);
                
                Transport transport = mailSession.getTransport();

                
                MimeMessage mensaje = new MimeMessage(mailSession);
                MimeBodyPart msg = new MimeBodyPart();
                
                mensaje.setFrom(direccionFrom);
                mensaje.addRecipients(javax.mail.Message.RecipientType.TO, direccionesInternetTo);
                mensaje.setSubject(subject);
                if(direccionesReplyTo != null) {
                    InternetAddress r[] = new InternetAddress[direccionesReplyTo.length];
                    for(int i = 0; i < direccionesReplyTo.length; i++)
                    {
                        r[i] = new InternetAddress(direccionesReplyTo[i]);
                    }

                    mensaje.setReplyTo(r);
                }
                if(direccionesInternetCc != null) {
                    mensaje.addRecipients(javax.mail.Message.RecipientType.CC, direccionesInternetCc);
                }
                if(direccionesInternetBcc != null) {
                    mensaje.addRecipients(javax.mail.Message.RecipientType.BCC, direccionesInternetBcc);
                }
                if(!inReplyTo.equals("")) {
                    mensaje.addHeader("In-Reply-To", inReplyTo);
                }
                if(!references.equals("")) {
                    mensaje.addHeader("References", references);
                }
                if(!comments.equals("")) {
                    mensaje.addHeader("Comments", comments);
                }
                if(!palabrasClave.equals("")) {
                    mensaje.addHeader("Keywords", palabrasClave);
                }
                if(!priority.equals("")) {
                    mensaje.addHeader("Priority", priority);
                }
                for(int i = 0; i < camposAdicionalesFormateados.size(); i++) {
                    mensaje.addHeaderLine((String)camposAdicionalesFormateados.get(i));
                }
                
                msg.setText(cuerpo + (signature.equals("") ? "" : StringUtils.EOL + "-- " + StringUtils.EOL + signature));
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(msg);
                
                if (attachments != null && attachments.size() > 0) {
                	Iterator<File> iter = attachments.iterator();
                	while (iter.hasNext()) {
                		File archivo = iter.next();
                		MimeBodyPart file = new MimeBodyPart();
                   	 	file.attachFile(archivo);
                   	 	mp.addBodyPart(file);
                	}
                }

                //mensaje.setText(cuerpo + (signature.equals("") ? "" : StringUtils.EOL + "-- " + StringUtils.EOL + signature));
                //mensaje.setContent(cuerpo + (signature.equals("") ? "" : StringUtils.EOL + "-- " + StringUtils.EOL + signature), "text/plain");
                mensaje.setContent(mp);
                
                
                //Transport.send(mensaje);
                
                //transport.connect();
                //transport.sendMessage(mensaje, mensaje.getAllRecipients());
                //transport.close();

        		
                //Transport transport = sesion.getTransport("smtp");
        		transport.connect(host, username, pass);
        		transport.sendMessage(mensaje, mensaje.getAllRecipients());
        		transport.close();
        		
                

                return EXITO;
            }
            catch(Exception ex)
            {
                logger.fatal("Excepcion! [" + ex.getMessage() + "]");
                logger.fatal("StackTrace [" + ErroresUtils.extraeStackTrace(ex) + "]");
            }
            return FALLOENELENVIO;
        }
        return EXITO;
    }
    
    

    private static String[] filtraDirecciones(String datos[]) {
        Vector v = new Vector();
        String resultado[] = null;
        if(datos != null && datos.length != 0) {
            for(int i = 0; i < datos.length; i++) {
                String dato = datos[i];
                if(dato != null && !dato.trim().equals("")) {
                    if(dato.indexOf(",") >= 0) {
                        String varios[] = StringUtils.divide(dato, ",");
                        for(int j = 0; j < varios.length; j++) {
                            if(varios[j] != null && !varios[j].trim().equals("")) {
                                String direccion = varios[j].trim();
                                try {
                                    InternetAddress a = new InternetAddress(direccion);
                                    v.add(direccion);
                                }
                                catch(Exception e) {
                                    logger.error("La direcci\363n [" + direccion + "] es ilegal y ser\341 descartada.");
                                }
                            }
                        }

                    } 
                    else {
                        String direccion = dato.trim();
                        try {
                            InternetAddress a = new InternetAddress(direccion);
                            v.add(direccion);
                        }
                        catch(Exception e) {
                            logger.error("La direcci\363n [" + direccion + "] es ilegal y ser\341 descartada.");
                        }
                    }
                }
            }

            if(v.size() > 0) {
                resultado = new String[v.size()];
                for(int i = 0; i < v.size(); i++) {
                    resultado[i] = (String)v.elementAt(i);
                }

            }
        }
        return resultado;
    }

    private static InternetAddress[] direccionesInternet(String direcciones[]) {
        if(direcciones == null) {
            return null;
        }
        Vector v = new Vector();
        for(int i = 0; i < direcciones.length; i++) {
            try {
                InternetAddress a = new InternetAddress(direcciones[i]);
                v.add(a);
            }
            catch(Exception e) {
                logger.fatal("La direcci\363n [" + direcciones[i] + "] es ilegal y ser\341 descartada.");
            }
        }

        if(v.size() == 0) {
            return null;
        }
        InternetAddress todas[] = new InternetAddress[v.size()];
        for(int i = 0; i < v.size(); i++) {
            todas[i] = (InternetAddress)v.get(i);
        }

        return todas;
    }

    public static boolean direccionValida(String direccion) {
        if(direccion == null || direccion.trim().equals("")) {
            return false;
        }
        int posicion1 = direccion.indexOf('@');
        int posicion2 = direccion.lastIndexOf('@');
        if(posicion1 != posicion2) {
            return false;
        }
        if(posicion1 == -1) {
            return false;
        }
        if(direccion.substring(posicion1 + 1).indexOf('.') == -1) {
            return false;
        }
        boolean esValida = false;
        try {
            InternetAddress a = new InternetAddress(direccion);
            esValida = true;
        }
        catch(Exception e) {
            esValida = false;
        }
        return esValida;
    }
}

