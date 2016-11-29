/**
* Universidad Del Valle de Guatemala
* Pablo Arriola 131115
*/




import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Clase que genera el analizador léxico en el 
 * lenguaje de Java
 * @author Pablo
 */
public class LexerGenerator implements RegexConstants{
    
    private final HashMap<Integer,String> cadena;
    private String nombreArchivo;
    private final HashMap<String, String> cadenaCompleta;
    private final HashMap<String, String> tokensExpr;
    private final TreeMap<String,String> keyMap;
    private final Stack pilaConcatenacion;
    private final Stack pilaAvanzada;
    private String ignoreSets =  ;
    private final String ANY = [!-#]charOr[%-.]charOr[@-Z]charOr[^-z];
    private final HashMap<String, Boolean> verKeywords;
   
    
    /**
     * Constructor
     * @param cadena HashMap con la cadena del input
     */
    public LexerGenerator(HashMap cadena){
        this.verKeywords = new HashMap();
        this.pilaConcatenacion = new Stack();
        this.keyMap = new TreeMap();
        this.tokensExpr = new HashMap();
        this.cadenaCompleta = new HashMap();
        this.pilaAvanzada = new Stack();
        this.cadena=cadena;
    }
    
    /**
     * Método para encontrar el nombre del archivo a generar
     */
    public void encontrarNombre(){
        
        for (Map.Entry<Integer, String> entry : cadena.entrySet()) {
            String value = entry.getValue();
            if (value.contains(COMPILER)){
                value = value.trim();
                int index = value.indexOf(R);
                this.nombreArchivo = value.substring(++index,value.length());
                this.nombreArchivo = this.nombreArchivo.trim();

            }

        }
    
    }
    
   /**
    * Genera la  estructura de la clase analizadora
    */
    public void generarClaseAnalizadora() {

        String scanner_total = (
            /**
             * Nombre del archivo: this.nombreArchivo.java
             * Universidad del Valle de Guatemala
           
            **/
            
       
          
           
          
            
            public class this.nombreArchivo {
            
            private Simulacion sim = new Simulacion();
            private ArrayList<Automata> automatas = new ArrayList();
            private HashMap<Integer,String> input;   
            private ArrayList keywords = new ArrayList(););
           
            scanner_total +=
            private String ignoreSets = \ |	\;
            private ArrayList<Token> tokensAcumulados = new ArrayList();    
            private ArrayList<Token> tokens = new ArrayList();
            private String tk  = \\;
            private Errors errores = new Errors();
                
            public  + this.nombreArchivo(HashMap input){
            this.input=input;
            
             
            }    
                
        ;

        scanner_total += crearAutomatasTexto();
        scanner_total += generar();
        scanner_total += metodoRevisar();
       // scanner_total += keyWords();
       // scanner_total += ignoreWords();
        scanner_total+=};
        
        ReadFile fileCreator = new ReadFile();
        fileCreator.crearArchivo(scanner_total, nombreArchivo);
        
    }
    
    /**
     * Obtiene los sets a ignorar
     */
    public void ignoreSets(){
        for (Map.Entry<Integer, String> entry : cadena.entrySet()) {
               String value = entry.getValue();
               int lineaActual = entry.getKey();
               if (this.cadena.get(lineaActual).contains(IGNORE)){
                    if (value.contains(\')){
                        value = value.replaceAll(\', );
                    }else if (value.contains(\)){
                        value = value.replaceAll(\,);
                    }
                    /*String whitespace = 
                            
                            String regex_1 =   value.substring(6,value.indexOf(.));;
		AFNConstruct ThomsonAlgorithim_1 = new AFNConstruct(regex_1);
		
		Automata temp_1 = ThomsonAlgorithim_1.afnSimple(regex_1);
		temp_1.setTipo(WHITESPACE);
		automatas.add(temp_1);*/
                    tokensExpr.put(WHITESPACE, value.substring(6,value.indexOf(.)));
                    
                   ignoreSets = (value.substring(6,value.indexOf(.)));
                   
               }
        
        }
        System.out.println(ignoreSets);
    }
    
    /**
    * Método para pasara character y keywords a expresiones regulares
    */
    public void generarCharactersYKeywords(){
        ignoreSets();
        RegexConverter convert = new RegexConverter();
        cadenaCompleta.put(ANY, convert.abreviacionOr(ANY));
        
        for (Map.Entry<Integer, String> entry : cadena.entrySet()) {
            String value = entry.getValue();
            if (value.contains(CHARACTERS)){
                int lineaActual = entry.getKey();
                while(true){
                    lineaActual = avanzarLinea(lineaActual);
                    if (this.cadena.get(lineaActual).contains(KEYWORDS)||this.cadena.get(lineaActual).contains(TOKENS))
                        break;
                    String valor = this.cadena.get(lineaActual);
                    
                    valor = valor.trim();
                    int index = valor.indexOf(=);
                    String ident = valor.substring(0,index);
                    String revisar  = valor.substring(++index,valor.length()-1);
                    revisar = revisar.trim();
                    revisar = crearCadenasOr(revisar);
                  //  System.out.println(ident);
                  //  System.out.println(revisar);
                    
                    
                    cadenaCompleta.put(ident.trim(), revisar);
                    

                    //System.out.println(cadenaCompleta);

                }

            }
            if (value.contains(KEYWORDS)&&!value.contains(EXCEPT)){
                int lineaActual = entry.getKey();
                lineaActual = avanzarLinea(lineaActual);
                while(true){
                    
                    if (this.cadena.get(lineaActual).contains(END)||this.cadena.get(lineaActual).contains(TOKENS))
                        break;
                    String valor = this.cadena.get(lineaActual);
                    valor = valor.trim();
                    int index = valor.indexOf(=);
                    String ident = valor.substring(0,index);
                    String revisar  = valor.substring(++index,valor.length()-1);
                    revisar = revisar.trim();
                    //revisar = crearCadenasOr(revisar);
                    revisar = revisar.replaceAll(\, );
                   //tokensExpr.put(ident.trim(), revisar);
                   
                           
                    keyMap.put(ident.trim(),revisar);
                    tokensExpr.put(ident.trim(),revisar);
                            
                      
                    lineaActual = avanzarLinea(lineaActual);
                    
                }
            }

        }
        //System.out.println(cadenaCompleta);
        
    }
    
    /**
     * Método que lee el archivo de Cocol/R y
     * crea las expresionres de los tokens
     */
    public void generarTokens(){
         for (Map.Entry<Integer, String> entry : cadena.entrySet()) {
            String value = entry.getValue();
            if (value.contains(TOKENS)){
                int lineaActual = entry.getKey();
                while(true){
                    lineaActual = avanzarLinea(lineaActual);
                    if (this.cadena.get(lineaActual).contains(END)||this.cadena.get(lineaActual).contains(IGNORE)
                        ||this.cadena.get(lineaActual).contains(PRODUCTIONS))
                        break;
                    String valor = this.cadena.get(lineaActual);
                    
                     
                    
                    valor = valor.trim();
                    int index = valor.indexOf(=);
                    String ident = valor.substring(0,index);
                    String revisar  = valor.substring(++index,valor.length()-1);
                    if (revisar.contains(EXCEPT)){
                        revisar = revisar.substring(0,revisar.indexOf(EXCEPT)).trim();
                        verKeywords.put(ident, Boolean.TRUE);
                    }
                    revisar = revisar.trim();
                    
                    System.out.println();
                      System.out.println(ident);
                     System.out.println(revisar);      
                    revisar = revisar.replaceAll(\\{, charAbrirParentesis);
                    revisar = revisar.replaceAll(\\}, charCerrarParentesischarKleene);
                    revisar = revisar.replaceAll((\\[)(?=(?:[^\']|[\|'][^\']*\)*), charAbrirParentesis);
                    revisar = revisar.replaceAll((\\])(?=(?:[^\']|[\|'][^\']*\)*),charCerrarParentesis +charInt);
                    revisar = revisar.replaceAll(\\|,charOr);
                    revisar = formatRegex(revisar);
                     System.out.println(revisar);
                    for (Map.Entry<String, String> entryRegex : cadenaCompleta.entrySet()) {
                        
                        if (revisar.contains(entryRegex.getKey())){
                            revisar = revisar.replaceAll(entryRegex.getKey().trim(), entryRegex.getValue());
                        }
                        
                    }
                    System.out.println(enmedio);
                    System.out.println(revisar);
                    revisar = fixString(revisar);
                    
                    //revisar = revisar.replaceAll(\\((?=(?:[^\']|[\|'][^\']*\)*$),);
                    
                    /* if (revisar.startsWith(\)||revisar.startsWith(\\)||revisar.startsWith(\')){
                        revisar = revisar.replaceAll(\\((?!(?:[^\]*\[^\]*\)*[^\]*$), charAbrirParentesis);
                        revisar = revisar.replaceAll(\\)(?=(?:[^\]*\[^\]*\)*[^\]*$), charCerrarParentesis);
                     }
                     else{
                        revisar = revisar.replaceAll(\\((?=(?:[^\]*\[^\]*\)*[^\]*$),charAbrirParentesis);
                        revisar = revisar.replaceAll(\\)(?=(?:[^\]*\[^\]*\)*[^\]*$),charCerrarParentesis);
                     }            
                    */
                    // revisar = revisar.replaceAll(\\)(?=(?:[^\]*\[^\]*\)*[^\]*$),charCerrarParentesis);
                    //revisar = revisar.replaceAll(\\((?=(?:[^\]*\[^\]*\)*[^\]*$),charAbrirParentesis);
                    //revisar = revisar.replaceAll(\\)(?=(?:[^\]*\[^\]*\)*[^\]*$),charCerrarParentesis);
                    
                    //revisar = revisar.replaceAll('(?=(?:[^\]*\[^\]*\)*[^\]*$),);
                    //revisar = revisar.replaceAll(\, );
                   // revisar = revisar.replaceAll((\)(?=(?:[^\']|[\|'][^\']*\)*$),);
                   // revisar = revisar.replaceAll(\', );
                    //revisar = revisar.replaceAll(\\\, );
                   /* System.out.println(revisar);
                    revisar = poner(revisar);
                    System.out.println(revisar);
                    System.out.println(quitando);
                    revisar = quitar(revisar);
                    revisar = poner2(revisar);
                    revisar = quitar2(revisar);*/
                 //quitar parentesis
                 //quitar doble quotes
                 //quitar simple quotes
                   
                    if (revisar.startsWith('))
                        revisar = revisar.substring(1);
                    if (revisar.equals(charCerrarParentesis))
                        revisar = );
                    if (revisar.equals(charAbrirParentesis))
                        revisar = (;
                    revisar = revisar.replaceAll(\\s,);
                    //System.out.println(ident);
                   // System.out.println(revisar);
                    tokensExpr.put(ident.trim(), revisar);
                  
                    
                    System.out.println(revisar);
                    

                }

            }
         }
       // System.out.println(tokensExpr);
    }
    
    /**
     * quitar parentesis, double quotes, simple quotes
     * @param eval
     * @return String con formato para autómatas
     */
    public String formatRegex(String eval){
        String returnString=;
        
        for (int i =0;i<eval.length();i++){
            Character ch = eval.charAt(i);
            
            if (i>0){
                if (ch=='('&&eval.charAt(i-1)!='\'){
                    ch = charAbrirParentesis;
                }
                if (i>1){
                    if (ch=='\'&&(eval.charAt(i-1)!='\\')){
                        continue;
                    }
                    if (ch=='\'&&eval.charAt(i-2)=='\\'){
                        continue;
                    }
                }
                if (ch=='\''&&(eval.charAt(i-1)!='\\')&&eval.charAt(i-1)!='\')
                    continue;
                
            }
            if (i==0){
                if (ch=='(')
                    ch = charAbrirParentesis;
                if (ch=='\')
                    continue;
            }
            if (i+1<eval.length()){
                if (ch==')'&&eval.charAt(i+1)!='\'){
                    ch = charCerrarParentesis;
                }
            }
            else{
                if (ch==')')
                    ch = charCerrarParentesis;
            }
            returnString += ch;   
        }
        
        
        return returnString;
    }
    
    /**
     * Método para agregar los caracteres de escape que lo necesiten
     * @param eval
     * @return String modificado con las correciones necesarias
     */
    public String fixString(String eval){
        String returnString = ;
      
        for (int i=0;i<eval.length();i++){
             String pos = ;
            Character ch = eval.charAt(i);
            if (ch == '\'){//agregar escape chars en caso de que no lo tenga
                if (eval.charAt(i-1)!='\\'){
                    pos=\\;
                }
            }
            if (i+1<eval.length()){//eliminar dos caracteres or seguidos
                if (ch==charOr&&eval.charAt(i+1)==charOr)
                    continue;
            }
           
            returnString += pos +ch;
        }
        
        return returnString;
    }
    
    
    public String quitar(String eval){
        String returnString =;
        char[] charArray = eval.toCharArray();
        for (int i=0;i<charArray.length;i++){
           
          
            if (i>1 && i<eval.length()-3){
                if (charArray[i]==''&&(((charArray[i-1]=='\\')||charArray[i+1]!='\'||charArray[i+1]=='\''))&&charArray[i+1]!='')
                    continue;
               
            }
            
            if (i<=1){
                if (charArray[i+1]=='\''&&eval.charAt(i)=='\')
                    continue;
            }
            if (i>=charArray.length-3){
                if (charArray[i]==''&&charArray[i-1]!='\\')
                    continue;
            }
            returnString +=charArray[i];
        }
        
        return returnString;
    }
    public String poner(String eval){
        String returnString =;
        for (int i =0;i<eval.length();i++){
            Character ch = eval.charAt(i);
            String pos =;
            String pos2 = ;
            String pos1 = ;
            if (i>0){
                if ((ch=='\''||ch=='\\'||ch=='')&&eval.charAt(i-1)==charOr&&eval.charAt(i+1)==charOr){
                    pos=\\;
                    pos1=\;
                    pos2=pos1;
                }
            }
            returnString += pos1+pos+ch+pos2;
        }
        return returnString;
    }
    public String poner2(String eval){
        String returnString =;
        for (int i =0;i<eval.length();i++){
            Character ch = eval.charAt(i);
            String pos =;
            String pos2 = ;
            String pos1 = ;
            if (i>0&&i<eval.length()-2){
                if ((ch=='\''||ch=='\')&&eval.charAt(i-1)!='\\'&&!(eval.charAt(i+2)=='\''||eval.charAt(i-2)=='\'')){
                    pos=\\;
                   // pos1=\;
                   // pos2=pos1;
                }
            }
            if (i==0){
                if (eval.charAt(i+1)!='\\'&&(ch=='\''||ch=='\')){
                    pos=\\;
                    /*pos1=\;
                    pos2=pos1;*/
                }
            }
            if (i>=eval.length()-2){
                if (eval.charAt(i-1)!='\\'&&ch=='\''&&eval.charAt(i-1)!='*'){
                    pos=\\;
                    /*pos1=\;
                    pos2=pos1;*/
                }
            }
        
          returnString += pos+ch;
        }
        return returnString;
        
    }
    
    /**
     * Crea una cadena con operaciones or en cada caracter
     * En caso de haber concatenaciones calcula si tiene que
     * ser por la izquierda o derecha
     * @param cadena a evaluar
     * @return String con la cadena modificada
     */
    public String crearCadenasOr(String cadena){
        
        String or = ;
        
        or = cadenasOrLista(cadena);
        if (!or.isEmpty()&&!cadena.contains()){
            //System.out.println(or);
            return or;
        }
        if (cadena.equals('+')){
            //System.out.println(cadena);
            return ;
        }
        if (cadena.equals('-')){
            //System.out.println(cadena);
            return -;
        }
        if ((cadena.startsWith(\)||cadena.startsWith(\'))&&(!cadena.contains())){
             
            try{
               
            cadena=  cadena.substring(cadena.indexOf(\)+1,cadena.lastIndexOf(\));
           
            }catch(Exception e){}
            
             try{
                
            cadena=  cadena.substring(cadena.indexOf(\')+1,cadena.lastIndexOf(\'));
           
            }catch(Exception e){}
            
            for (int i = 0;i<cadena.length();i++){
                Character c = cadena.charAt(i);
               
                    
                        if (c=='\\'){
                           
                            or += c;
                            or += cadena.charAt(i+1);
                            i++;
                           
                        }
                         if (c == '$'){
                            or += \\ + c;
                        }
                        else if (i<=cadena.length()-2){
                            or += c;
                            or += charOr;
                        }            
                        else if (i>cadena.length()-2)
                            or +=c;
                        

                
            }
           
            
           
        }
        else {
            or = cadena;
            if(cadena.contains()&&!cadena.contains(-)){
                if ((cadena.contains(\)||cadena.contains(\'))&&!cadena.contains(..)){
                int cantidadConcatenaciones = count(cadena,'+');
                if (cantidadConcatenaciones>1){
                   // System.out.println(cadena.lastIndexOf());
                    //System.out.println(cadena.substring(0, cadena.indexOf(, cadena.indexOf() + 1)));
                    pilaConcatenacion.push(cadena.substring(cadena.indexOf(, cadena.indexOf() + 1)));
                   // System.out.println(pilaConcatenacion);
                }
               // System.out.println(cantidadConcatenaciones);
                int preIndex=0;
                if (cadena.contains(\))
                     preIndex = cadena.indexOf((\));
                 if (cadena.contains(\'))
                     preIndex = cadena.indexOf((\'));
                String w = cadena.substring(preIndex+1);
                int postIndex = w.length()-1;
                if (w.contains(\))
                    postIndex = w.indexOf((\));
                if (w.contains(\'))
                    postIndex = w.indexOf((\'));
                String wFinal = cadena.substring(preIndex,preIndex+postIndex+2);
                
                //System.out.println(wFinal);
                String cadenaOr = crearCadenasOr(wFinal);
                //calcular si se concatena a la izquierda o derecha
                int lado = calcularConcatenacion(or);
                if (lado == -1){
                    
                    or = charAbrirParentesis+buscarExpr(or) +charCerrarParentesis+charOr+charAbrirParentesis+ cadenaOr+charCerrarParentesis;
                }
                else if (lado == 1){
                    
                    or = charAbrirParentesis+cadenaOr +charCerrarParentesis+charOr+charAbrirParentesis+ buscarExpr(or)+charCerrarParentesis;
                }
                
                while (!pilaConcatenacion.isEmpty()){
                    String faltante = (String)pilaConcatenacion.pop();
                    cantidadConcatenaciones = count(faltante,'+');
                    if (cantidadConcatenaciones>1){
                        pilaConcatenacion.push(faltante.substring(faltante.indexOf(, faltante.indexOf() + 1)));
                        faltante = (faltante.substring(0, faltante.indexOf(, faltante.indexOf() + 1)));

                    }
                        or = concatenacion(or,faltante);
                }
                }
                else if (!cadena.contains(..)){
                    int cantidadConcatenaciones = count(cadena,'+');
                    if (cantidadConcatenaciones>1){
                        // System.out.println(cadena.lastIndexOf());
                         //System.out.println(cadena.substring(0, cadena.indexOf(, cadena.indexOf() + 1)));
                             pilaConcatenacion.push(cadena.substring(cadena.indexOf(, cadena.indexOf() + 1)));
                        // System.out.println(pilaConcatenacion);
                    }
                    String subcadena = cadena.substring(0,cadena.indexOf());
                    String ident1 = buscarExpr(subcadena);
                    String subcadena2 = cadena.substring(cadena.indexOf()+1);
                    String ident2 = buscarExpr(subcadena2);
                     int lado = calcularConcatenacion(or);
                if (lado == -1){
                    
                    or = charAbrirParentesis+ident1 +charCerrarParentesis+charOr+charAbrirParentesis+ ident2+charCerrarParentesis;
                }
                else if (lado == 1){
                    
                    or = charAbrirParentesis+ident2 +charCerrarParentesis+charOr+charAbrirParentesis+ ident1+charCerrarParentesis;
                }
                  while (!pilaConcatenacion.isEmpty()){
                    String faltante = (String)pilaConcatenacion.pop();
                    cantidadConcatenaciones = count(faltante,'+');
                    if (cantidadConcatenaciones>1){
                        pilaConcatenacion.push(faltante.substring(faltante.indexOf(, faltante.indexOf() + 1)));
                        faltante = (faltante.substring(0, faltante.indexOf(, faltante.indexOf() + 1)));

                    }
                    else
                         faltante = (faltante.substring(faltante.indexOf()+1));
                    or = concatenacionIdent(or,faltante);
                }
                    
                }
                else {
                    int cantidadConcatenaciones = count(cadena,'+');
                     String subcadena2=;
                    if (cantidadConcatenaciones>1){
                        // System.out.println(cadena.lastIndexOf());
                         //System.out.println(cadena.substring(0, cadena.indexOf(, cadena.indexOf() + 1)));
                             pilaConcatenacion.push(cadena.substring(cadena.indexOf(, cadena.indexOf() + 1)));
                        // System.out.println(pilaConcatenacion);
                              subcadena2 = cadena.substring(cadena.indexOf()+1,cadena.indexOf(, cadena.indexOf() + 1));
                    }
                    else
                          subcadena2 = cadena.substring(cadena.indexOf()+1);
                    String subcadena = cadena.substring(0,cadena.indexOf());
                  
                    String list1 = cadenasOrLista(subcadena);
                    String list2 = cadenasOrLista(subcadena2);
                    if (list1.isEmpty())
                        list1 = crearCadenasOr(subcadena);
                    if (list2.isEmpty())
                        list2 = crearCadenasOr(subcadena2.trim());
                    or = list1+charOr+list2; 
                      while (!pilaConcatenacion.isEmpty()){
                    String faltante = (String)pilaConcatenacion.pop();
                    cantidadConcatenaciones = count(faltante,'+');
                    if (cantidadConcatenaciones>1){
                        pilaConcatenacion.push(faltante.substring(faltante.indexOf(, faltante.indexOf() + 1)));
                        faltante = (faltante.substring(faltante.indexOf()+1, faltante.indexOf(, faltante.indexOf() + 1)));

                    }
                    else
                         faltante = (faltante.substring(faltante.indexOf()+1));
                    or =  or +charOr+crearCadenasOr(faltante.trim());
                    
                }
                    
                    return charAbrirParentesis+or+charCerrarParentesis;
                }
            }else if (cadena.contains(-)&&!cadena.contains()){
                if ((cadena.contains(\)||cadena.contains(\'))&&!cadena.contains(..)){
                   
                    int cantidadConcatenaciones = count(cadena,'-');
                    String subcadena2=;
                    if (cantidadConcatenaciones>1){
                       // System.out.println(cadena.lastIndexOf());
                        //System.out.println(cadena.substring(0, cadena.indexOf(, cadena.indexOf() + 1)));
                        pilaConcatenacion.push(cadena.substring(cadena.indexOf(-, cadena.indexOf(-) + 1)));
                       // System.out.println(pilaConcatenacion);
                         subcadena2 = cadena.substring(cadena.indexOf(-)+1,cadena.indexOf(-, cadena.indexOf(-) + 1));
                    }
                     else
                            subcadena2 = cadena.substring(cadena.indexOf(-)+1);
                    String subcadena = cadena.substring(0,cadena.indexOf(-));
                   // System.out.println(cantidadConcatenaciones);
                    int preIndex=0;
                    String expr  =  buscarExpr(subcadena2);
                    subcadena2 = subcadena2.trim();
                        
                    if (!expr.isEmpty()){
                        expr = expr.replaceAll(charAbrirParentesis, );
                        expr = expr.replaceAll(charCerrarParentesis, );
                        subcadena2 = expr;
                    }
                    if (subcadena2.startsWith(\))
                        subcadena2 = subcadena2.substring(1);
                    if (subcadena2.endsWith(\))
                        subcadena2 =subcadena2.substring(0,subcadena2.length()-1);
                    else if (subcadena2.startsWith(\')){
                        subcadena2 = subcadena2.substring(1);
                    if (subcadena2.endsWith(\'))
                        subcadena2 =subcadena2.substring(0,subcadena2.length()-1);
                    }
                    /*String tiene =  buscarExpr(subcadena);
                    String quitar = cadenaOr;*/
                    or = buscarExpr(subcadena);
                    int indexQuitar=0;
                    if (or.contains(subcadena2)){
                       or = or.replaceAll(subcadena2, );
                    }
                    or = balancear(or);
                    while (!pilaConcatenacion.isEmpty()){
                        String faltante = (String)pilaConcatenacion.pop();
                        cantidadConcatenaciones = count(faltante,'-');
                        if (cantidadConcatenaciones>1){
                            pilaConcatenacion.push(faltante.substring(faltante.indexOf(-, faltante.indexOf(-) + 1)));
                            faltante = (faltante.substring(faltante.indexOf(-)+1, faltante.indexOf(-, faltante.indexOf(-) + 1)));

                        }  else
                             faltante = (faltante.substring(faltante.indexOf(-)+1));
                        expr  =  buscarExpr(faltante);
                        faltante = faltante.trim();
                        
                        if (!expr.isEmpty()){
                            expr = expr.replaceAll(charAbrirParentesis, );
                            expr = expr.replaceAll(charCerrarParentesis, );
                            faltante = expr;
                        }
                       
                        if (faltante.startsWith(\'))
                            faltante = faltante.substring(1);
                        
                        if (faltante.endsWith(\'))
                            faltante =faltante.substring(0,faltante.length()-1);
                        
                        else if (faltante.startsWith(\)){
                            faltante = faltante.substring(1);
                            if (faltante.endsWith(\))
                                faltante =faltante.substring(0,faltante.length()-1);
                        }
                        if (or.contains((faltante))){
                            or = or.replaceAll(faltante,);
                           // or = or.replaceAll(faltante,);
                           
                        }
                        System.out.println(or);
                        or = balancear(or);
                        System.out.println(or);
                    }
                } 
            }else if (cadena.contains()&&cadena.contains(-)){
             String mutador=;
             String original=;
              int indexOperadoresPlus = cadena.indexOf();
              int indexOperadoresMin  =  cadena.indexOf(-);
              if (indexOperadoresPlus<indexOperadoresMin){
                  mutador = cadena.substring(0,indexOperadoresPlus);
                  original = cadena.substring(indexOperadoresPlus);
              }
              else{
                  mutador = cadena.substring(0,indexOperadoresMin);
                  original = cadena.substring(indexOperadoresMin);
              }
              pilaAvanzada.push(original);
              while (!pilaAvanzada.isEmpty()){
                  String actual = (String)pilaAvanzada.pop();
                  
              }
              
            }
            
        }
        
        return charAbrirParentesis+or+charCerrarParentesis;
     }
    
    /**
     * Crea las listas con los caracteres deseados, desde char hasta otro char
     * @param cadena
     * @return 
     */
    public String cadenasOrLista(String cadena){
        String or =;
       
        if (cadena.contains(CHR)||cadena.contains(..)){
                if (cadena.contains(CHR)){
                int empieza = Integer.parseInt(cadena.substring(cadena.indexOf(()+1,cadena.indexOf())));
                int termina = Integer.parseInt(cadena.substring(cadena.lastIndexOf(()+1,cadena.lastIndexOf())));
                
               
                RegexConverter convert = new RegexConverter();
                or = convert.abreviacionOr([(char)(empieza)-(char)(termina)]);
                }
                else{
                    String empieza = (cadena.substring(cadena.indexOf(\')+1,cadena.indexOf(\', cadena.indexOf(\') + 1)));
                    
                    String termina = (cadena.substring(cadena.lastIndexOf(\')-1,cadena.lastIndexOf(\')));
                    RegexConverter convert = new RegexConverter();
                    or =  convert.abreviacionOr([(empieza)-(termina)]);
                }
              
            }
        return or;
    }
    
    public String concatenacionIdent(String anterior, String actual){
        return anterior + charOr + buscarExpr(actual);
    }
    
    /**
     * Método auxiliar que se llama cuando hay más de una concatenación
     * @param anterior String con lo ya concatenado
     * @param actual String con lo que falta concatenar
     * @return String con expresión regular
     */ 
    public String concatenacion(String anterior, String actual){
            String resultado = ;
            String cadenaOr=anterior;
            if (actual.contains(\))
                cadenaOr = crearCadenasOr(actual);
             if (actual.contains(\'))
                cadenaOr = crearCadenasOr(actual);
            //calcular si se concatena a la izquierda o derecha
            int lado = calcularConcatenacion(actual);
            if (lado == -1){

                resultado = charAbrirParentesis+buscarExpr(actual) +charCerrarParentesis+charOr+charAbrirParentesis+ cadenaOr+charCerrarParentesis;
            }
            else if (lado == 1){

                resultado = charAbrirParentesis+cadenaOr +charCerrarParentesis+charOr+charAbrirParentesis+ buscarExpr(actual)+charCerrarParentesis;
            }
        return resultado;
     }
    
    /**
     * Método para avanzar de línea, busca la línea que actual
     * @param lineaActual
     * @return lineaActual
     */
    public Integer avanzarLinea(int lineaActual){
       while (true){
           if (this.cadena.containsKey(++lineaActual))
               return lineaActual;
       }
    }
    
    /**
     * Método para calcular la posición a concatenar
     * @param str
     * @return 
     */
    public int calcularConcatenacion(String str){
        int posicion = 0;
        int posConc = str.indexOf()+1;
        String ident = buscarIdent(str);
        int indexIdent = str.indexOf(ident) + ident.length();
        if (indexIdent<posConc)
            return -1;
        else if (indexIdent>posConc)
            return 1;
        
        return posicion;
    }
    
    /**
     * Método para buscar un identificador en el archivo
     * @param search identificador a buscar
     * @return Devuelve el identificador
     */
    public String buscarIdent(String search){
        String res = ;
        for (Map.Entry<String, String> entry : cadenaCompleta.entrySet()) {
            String value = entry.getKey();
            if (search.contains(value)){
                return value;
                // res = entry.getValue();
                
                
            }

        }
        Errors errores = new Errors();
        errores.WarningIdent(identificador no declarado);
        //System.out.println(Identificador no declarado  );
        return res;
        
    }
    
    /**
     * Método para buscar los identificadores de keywords
     * @param search
     * @return true si fue encontrado
     */
    public boolean buscarIdentKey(String search){
       
        for (Map.Entry<String, Boolean> entry : verKeywords.entrySet()) {
            String value = entry.getKey();
            if (value.contains(search)){
                return true;
                // res = entry.getValue();
                
                
            }

        }
      
        return false;
        
    }
    
    /**
     * Método para buscar un identificador en el archivo
     * @param search identificador a buscar
     * @return devuelve expresión regular asociada al identificador
     */
    public String buscarExpr(String search){
        String res = ;
        for (Map.Entry<String, String> entry : cadenaCompleta.entrySet()) {
            String value = entry.getKey();
            if (search.trim().contains(value)){
                return entry.getValue();
                
                
                
            }

        }
        return res;
     }
    
    public String balancear(String subcadena){
        String subcadenaBal = ;
        for (int i = 0;i<subcadena.length();i++){
            Character ch = subcadena.charAt(i);
            if (i+1<subcadena.length()){
                if (ch != subcadena.charAt(i+1)){
                    subcadenaBal += ch;
                }
            }else
                subcadenaBal += ch;
        }
        return subcadenaBal;
    }
 
    /**
     * Método para calcular el número de ocurrencias de un character
     * @param s string completo 
     * @param c character a calcular ocurrencias
     * @return 
     */
    public  int count( final String s, final char c ) {
        final char[] chars = s.toCharArray();
        int count = 0;
        for(int i=0; i<chars.length; i++) {
          if (chars[i] == c) {
            count++;
          }
          if (i+1<chars.length){
              if (chars[i]=='\''&&chars[i+1]=='+'&&chars[i+2]=='\'')
                  count--;
        }
        }
        return count;
    }
   
    /**
     * Método para exportar los autómatas a texto plano
     * @return Devuelve un string con los autómatass
     */
    public String crearAutomatasTexto(){
       String afn = ;
       afn += ;
       afn += tpublic void automatas();
       afn += ;
       int counter = 0;
       for (Map.Entry<String, String> entry : tokensExpr.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (value.length()>1&&!value.trim().isEmpty()){
                afn += 
                      RegexConverter convert_counter= new RegexConverter();
                     String regex_counter = convert_counter.infixToPostfix(value);
                     AFNConstruct ThomsonAlgorithim_counter = new AFNConstruct(regex_counter);
                     ThomsonAlgorithim_counter.construct();
                     Automata temp_counter = ThomsonAlgorithim_counter.getAfn();
                     temp_counter.setTipo(key);
                       
                      if (buscarIdentKey(key)){
                          afn += + temp_counter.setExceptKeywords(true);
                      }
                     afn+=automatas.add(temp_counter);
                     
              
            }else{
                 afn += 
                     
                     AFNConstruct ThomsonAlgorithim_counter = new AFNConstruct();
                     Automata temp_counter=ThomsonAlgorithim_0.afnSimple(value);
                     temp_counter.setTipo(key);
                     automatas.add(temp_counter);
                
            }
            counter++;
        }
       afn += t;
       
       
       return afn;
   }
    
    /**
     * Métodos auxiliares para revisar autómatas
     * @return string con los métodos
     */
    public String generar(){
      String metodoVer= 
      /** \n 
     * Método para revisar que tipo de sub autómata es aceptado por una 
     * expresión regular
     * @param regex expresión regular a comparar
     * @param conjunto arreglo de autómatas
     */
    public void checkIndividualAutomata(String regex, ArrayList<Automata> conjunto,int lineaActual){
        
            ArrayList<Boolean> resultado = new ArrayList();
               
            ArrayList tks = tokenMasLargo(regex, conjunto, lineaActual);
            if (!(Boolean)tks.get(0)&&!((String)tks.get(1)).isEmpty()){
                errores.LexErr(lineaActual,regex.indexOf((String)tks.get(1)),regex,(String)tks.get(1));
            }
              
    }
    
    /**
     * Método que devuelve las posiciones en las que el valor que tiene 
     * en cada posicion es true
     * @param bool arreglo de booleanos
     * @return arreglo de enteros
     */
    public ArrayList<Integer>  checkBoolean(ArrayList<Boolean> bool){
        ArrayList<Integer> posiciones = new ArrayList();
       
        for (int i = 0;i<bool.size();i++){
            if (bool.get(i))
                posiciones.add(i);
        }
        return posiciones;
        
    }
        return metodoVer;
    }
    
    public String metodoRevisar() {
        String res = 
        public void revisar(){

        for (Map.Entry<Integer, String> entry : input.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            String[] parts = value.split(ignoreSets);
            tokens.clear(); 
             tk=;    
            for (String part : parts) {
               
                        
                     this.checkIndividualAutomata(part , automatas, key);
                       
            }
               System.out.println(tokens);
                 tokensAcumulados.addAll(tokens);
        }
                  try
          {
          + FileOutputStream fileOut =
          + new FileOutputStream(\../ParserGenerado/Tokens.ser\);
          + ObjectOutputStream out = new ObjectOutputStream(fileOut);
          + out.writeObject(tokensAcumulados);
          + out.close();
          + fileOut.close();
          + System.out.printf(\Serialized data is saved in /ParserGenerado/Tokens.ser\);
         }catch(IOException i)
         {
             + i.printStackTrace();
         }
               
              
    }
        
        res+=
        /**
         * Método para reconocer los tokens más simples
         * @param regex
         * @param conjuntoAut
         * @return boolean true si se crea un token, false si no es aceptado por ninguno.
         */
        public boolean tokenSimple(String regex,ArrayList<Automata> conjuntoAut){
                TreeMap<String,Automata> aceptados = new TreeMap(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    Integer a1 = o1.length();
                    Integer a2 = o2.length();
                    return a2-a1;
                }
            });  
        
            for (Automata automata: conjuntoAut){
                    if (sim.simular(regex,automata)){
                     aceptados.put(regex, automata);
                       
                    }
                }
            if (!aceptados.isEmpty()){ \n
                    tokens.add(new Token(aceptados.firstEntry().getValue().getTipo(),aceptados.firstKey(),aceptados.firstEntry().getValue().isExceptKeywords()));
                  
                    return true;
            }
            
            
            return false;
        };
                
        res+=
        /**
	* Método para simular y reconocer el caracter más largo
	* 
	* @param regex recibe la cadena a simular
        * @param conjuntoAut 
        * @param lineaActual linea del regex en el archivo        
        * @return boolean si es falso no fue reconocido nada
	*/
	public ArrayList tokenMasLargo(String regex, ArrayList<Automata> conjuntoAut, int lineaActual)
	{   
               boolean tokenSimple = tokenSimple(regex,conjuntoAut);
               if (tokenSimple){
                ArrayList casoBase = new ArrayList();
                casoBase.add(true);
                casoBase.add(regex);
				return casoBase;
                }
                
               while (tokens.isEmpty()||!regex.isEmpty()){
               
               + if (!tokenSimple(regex,conjuntoAut)){
                    try{

                       tk = regex.substring(regex.length()-1)+tk;

                       ArrayList returnBool = tokenMasLargo(regex.substring(0,regex.length()-1),conjuntoAut, lineaActual);

                       if (!(Boolean)returnBool.get(0)){
                           ArrayList casoRecursivo = new ArrayList();
                          casoRecursivo.add(false);
                          casoRecursivo.add((String)returnBool.get(1));
                           return casoRecursivo;
                         }
                       if ((Boolean)returnBool.get(0)){
                           regex = tk;
                           tk = ;
                       }
                    }catch(Exception e){
                         ArrayList casoRecursivo = new ArrayList();
                    casoRecursivo.add(false);
                    casoRecursivo.add(tk);
                        return casoRecursivo;
                    }//cierra catch
                }//cierra !if
                   else{
                ArrayList casoRecusivo = new ArrayList();
                casoRecusivo.add(true);
                return casoRecusivo;
                }
                
            }//cierra while
             if (regex.isEmpty()&&!tk.isEmpty()){
                 ArrayList casoRecusivo = new ArrayList();
                  casoRecusivo.add(false);
                casoRecusivo.add(tk);
                 return casoRecusivo;
            }
         ArrayList casoRecusivo = new ArrayList();
        casoRecusivo.add(false);
        casoRecusivo.add(regex);
        return  casoRecusivo;
	};
        return res;
    }
    
    public String ignoreWords(){
        String words = 
        public void ignoreWords(){
        
         words+=}
        
         
        return words;
    }
    /**
     * Método para generar Main del analizador
     */
    public void generarMain(){
        String res = 
        
        
        import java.util.HashMap;
        import javax.swing.JFileChooser;
        import java.io.File;

        /**
         *
         * @author Pablo
         */
        public class  resultadoGenerador Main {
    
            public static String EPSILON = ε;
            public static char EPSILON_CHAR = EPSILON.charAt(0);
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ReadFile read = new ReadFile();
        File file = new File(input.txt);
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        
            file = chooser.getSelectedFile();
        }
            
         HashMap input = read.leerArchivo(file);
                
        +this.nombreArchivo resGenerator = new this.nombreArchivo(input);
        resGenerator.automatas();
      
        resGenerator.revisar();
        
     
        
      
        
        }

    }
        
        ReadFile fileCreator = new ReadFile();
        fileCreator.crearArchivo(res, resultadoGeneradorMain);
        
    }
    
    public void generarSimulacion(){
        String simulacion = ;
        simulacion += 
            /** \n
            * Universidad Del Valle de Guatemala
           
            * Pablo Arriola 131115 \n
            */



            import java.io.File;
            import java.io.FileWriter;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.Stack;
          

            /**
             * Clase para utilizar el metodo de move, e-closure y simulacion de
             * un automata
             * Incluye también un método para generar archivos DOT
             * @author Pablo
             */
            public class Simulacion {

                private String resultado;
               private ArrayList caracteresIgnorar = new ArrayList();
                private Estado inicial_;
               public Simulacion(){
               caracteresIgnorar.add(resultadoGeneradorMain.EPSILON);
              
                simulacion+=}
    
           
                
            public HashSet<Estado> eClosure(Estado eClosureEstado){
                Stack<Estado> pilaClosure = new Stack();
                Estado actual = eClosureEstado;
                actual.getTransiciones();
                HashSet<Estado> resultado = new HashSet();

                pilaClosure.push(actual);
                while(!pilaClosure.isEmpty()){
                    actual = pilaClosure.pop();

                    for (Transicion t: (ArrayList<Transicion>)actual.getTransiciones()){

                        if ((caracteresIgnorar.contains(t.getSimbolo()))&&!resultado.contains(t.getFin())){
                            resultado.add(t.getFin());
                            pilaClosure.push(t.getFin());
                        }
                    }
                }
                resultado.add(eClosureEstado); //la operacion e-Closure debe tener el estado aplicado
                return resultado;
            }

            public HashSet<Estado> move(HashSet<Estado> estados, String simbolo){

                HashSet<Estado> alcanzados = new HashSet();
                Iterator<Estado> iterador = estados.iterator();
                while (iterador.hasNext()){

                    for (Transicion t: (ArrayList<Transicion>)iterador.next().getTransiciones()){
                        Estado siguiente = t.getFin();
                        String simb = (String) t.getSimbolo();
                         if (simb.equals(simbolo)){
                        alcanzados.add(siguiente);
                        }

                    }

                }
                return alcanzados;

            }

            public Estado move(Estado estado, String simbolo){
                ArrayList<Estado> alcanzados = new ArrayList();

                for (Transicion t: (ArrayList<Transicion>)estado.getTransiciones()){
                    Estado siguiente = t.getFin();
                    String simb = (String) t.getSimbolo();

                    if (simb.equals(simbolo)&&!alcanzados.contains(siguiente)){
                        alcanzados.add(siguiente);
                    }

                }

                return alcanzados.get(0);
            }

            /**
             * Método para simular un automata sin importar si es determinista o no deterministas
             * 
             * @param regex recibe la cadena a simular 
             * @param automata recibe el automata a ser simulado
             */
            public ArrayList simular(String regex, Automata automata)
            {
                  String returnString = \\;
                 String returnString2 = \\;
                 ArrayList returnArray = new ArrayList();
                 Estado final_ = null;
                Estado inicial = automata.getEstadoInicial();
                ArrayList<Estado> estados = automata.getEstados();
                ArrayList<Estado> aceptacion = new ArrayList(automata.getEstadosAceptacion());

                HashSet<Estado> conjunto = eClosure(inicial);
                char last = ' ';\n +
                int currentState = 0;\n +
                int finalState = 0;\n +
                int init = 0;
                for (Character ch: regex.toCharArray()){
                       /* if (ch == ' '){
                        currentState++;
                         break;
                        }*/
                    conjunto = move(conjunto,ch.toString());
                    HashSet<Estado> temp = new HashSet();
                    Iterator<Estado> iter = conjunto.iterator();

                    while (iter.hasNext()){
                       Estado siguiente = iter.next();
                       /**
                        * En esta parte es muy importante el metodo addAll
                        * porque se tiene que agregar el eClosure de todo el conjunto
                        * resultante del move y se utiliza un hashSet temporal porque
                        * no se permite la mutacion mientras se itera
                        */
                        temp.addAll(eClosure(siguiente)); 

                    }
                    if (conjunto.isEmpty())
                    returnString = (regex.substring(init,finalState));
                     if (temp.contains(aceptacion.get(0))){
                    finalState++;
                    }
                    currentState++;
                    conjunto=temp;


                }
                        
                if (returnString.isEmpty())
                returnString = (regex.substring(init,finalState));
                 returnString2 = regex.substring(currentState);
                 returnArray.add(returnString);
                returnArray.add(returnString2);
                 return returnArray;


               /* boolean res = false;

                for (Estado estado_aceptacion : aceptacion){
                    if (conjunto.contains(estado_aceptacion)){

                        res = true;
                    }
                }
                if (res){
                    //System.out.println(Aceptado);
                    //this.resultado = Aceptado;
                    return true;
                }
                else{
                    //System.out.println(NO Aceptado);
                    // this.resultado = No Aceptado;
                    return false;
                }
            }

            public String getResultado() {
                    return resultado;
                }*/
            }};



        ReadFile fileCreator = new ReadFile();
        fileCreator.crearArchivo(simulacion, Simulacion);
        
        
    }
    
    public void generarClaseToken(){
        String token =
      
        /**
        * Universidad Del Valle de Guatemala
        * Pablo Arriola 131115
        */

        
        import java.util.ArrayList;
        import java.util.HashSet;
        import java.util.Objects;
        import java.util.TreeMap;
        import java.io.Serializable;

        /**
         *
         * @author Pablo
         * @param <T>
         */
        public class Token<T> implements Serializable {

            private T id;
            private T lexema;
            private ArrayList keywords = new ArrayList();
            private HashSet<Token> tokens = new HashSet();
            private TreeMap<String,String> keyMap = new TreeMap();

            public Token(T id, T lexema,boolean revisarKey) {
                if (revisarKey)
                keyWords();
                ArrayList var = revisarKeywords(id,lexema);
                this.id = (T) var.get(0);
                this.lexema = (T) var.get(1);
            }

            public T getId() {
                return id;
            }

            public void setId(T id) {
                this.id = id;
            }

            public T getLexema() {
                return lexema;
            }

            public void setLexema(T lexema) {
                this.lexema = lexema;
            }

            public ArrayList revisarKeywords(T id, T lexema){
                ArrayList returnArray = new ArrayList();

                if (keyMap.containsKey((String)lexema)){
                    returnArray.add(keyMap.get((String)lexema));
                    returnArray.add(lexema);
                    return returnArray;
                }

                returnArray.add(id);
                returnArray.add(lexema);
                return returnArray;
            }

           public void keyWords(){
                for (Map.Entry<String, String> entry : keyMap.entrySet()) {
                     token+= keyMap.put(\entry.getValue()\,\entry.getKey()\);
               
            }
            token+=}
            token+=@Override
            public String toString() {
                //  return \<\ +id +\>\;
                return \<\ + id + \, \\\\ + lexema + \\\\>\;
           }

            @Override
            public int hashCode() {
                int hash = 3;
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (getClass() != obj.getClass()) {
                    return false;
                }
                final Token<?> other = (Token<?>) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                if (!Objects.equals(this.lexema, other.lexema)) {
                    return false;
                }
                return false;
            }



        }
        ReadFile fileCreator = new ReadFile();
        fileCreator.crearArchivo(token, Token);
        fileCreator.crearArchivoParser(token,Token);
            
            
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }
    
}
