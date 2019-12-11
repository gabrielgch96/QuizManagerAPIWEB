export class User{
    id: number;
    first_name: string;
    last_name: string;
    email: string;
    password: string;
    is_admin: boolean;

    constructor(first: string, last: string, 
        email: string, password: string, is_admin: boolean){

        this.first_name = first;
        this.last_name = last;
        this.email = email;
        this.password = password;
        this.is_admin = is_admin;
    }
}