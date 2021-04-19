package org.palina.security

import grails.validation.ValidationException
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional
@Secured('permitAll')
class RegisterController {

    static allowedMethods = [register: "POST"]

    def index() { }

    def register() {
        if(!params.password.equals(params.repassword)) {
            flash.message = "Password and Re-Password not match"
            redirect action: "index"
            return
        } else {
            try {
                def user = UserApp.findByUsername(params.username)?: new UserApp(username: params.username, password: params.password, fullname: params.fullname, address: params.address).save()
                def role = Role.get(params.role.id)
                if(user && role) {
                    UserRole.create user, role

                    UserRole.withSession {
                        it.flush()
                        it.clear()
                    }

                    flash.message = "You have registered successfully. Please login."
                    redirect controller: "login", action: "auth"
                } else {
                    flash.message = "Register failed"
                    render view: "index"
                    return
                }
            } catch (ValidationException e) {
                flash.message = "Register Failed"
                redirect action: "index"
                return
            }
        }
    }
}
