/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package register;

/**
 *
 * @author fabio
 */
public class NickContraseña{
   private static NickContraseña guardar;
    
    private String nickname;
    private String contraseña;

    private NickContraseña() {
    }
    
    public static NickContraseña getGuardar() {
        if (guardar == null) {
            guardar = new NickContraseña();
        }
        return guardar;
    }


    public String getNickname() { 
        return nickname; }
    public void setNickname(String nickname) { 
        this.nickname = nickname; }

    public String getContraseña() { 
        return contraseña; }
    public void setContraseña(String contraseña) { 
        this.contraseña = contraseña; }
    
}


// si quereis guardar variables o utilizarlas os pongo aqui lo que teneis que hacer:
//  private String varible;
//      public String getVariable(){
//      return variable
// }
//      public void setVariable(tipo varible){
//      this.variable o otro nombre seguido del tipo = varible;
// }

// en el codigo de otra pestaña
//          NickContraseña datos = NickContraseña.getGuardar();
//            datos.setvariable(variable2);
//            datos.setvariable(variable2);



