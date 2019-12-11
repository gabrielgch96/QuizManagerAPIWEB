import { Component, OnInit } from '@angular/core';
import { User } from '../../datamodel/user';
import { UserService } from '../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User[];
  incorrect: boolean;
  email: string ;
  password: string ;
  returnUrl: string;

  constructor(private userService: UserService,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router) {
      this.incorrect = false;
      if(this.authService.currentUserValue){
        this.router.navigate(['/questions']);
      }
    }

  ngOnInit() {
    this.email= "";
    this.password= "";
    this.user = [];

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/exams';
  }

  login(){

    this.authService.loginUser(this.email, this.password).pipe(first()).subscribe(
      data => this.router.navigate([this.returnUrl]),
      error => console.log(error)
    )
    this.incorrect = true;
    /*this.userService.validateUser(this.email, this.password)
      .subscribe((data) =>
      {
        this.user = data;
        console.log(this.user);
        if(this.user.length > 0){
          console.log("HERE");
          this.router.navigate(['questions']);
      }
      });*/

  }

}
