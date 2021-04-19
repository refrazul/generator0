import org.palina.security.UserPasswordEncoderListener
import org.palina.security.CustomUserDetailsService


// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    userDetailsService(CustomUserDetailsService)

}
