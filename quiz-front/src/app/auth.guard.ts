import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from './services/authentication.service';
import { User } from './datamodel/user';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router,
    private authService: AuthenticationService){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
      const currentUser: User = this.authService.currentUserValue;
      if(currentUser)
      {
        return true;
      }
      else
      {
        this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}})
        return false;
      }
    }
  
}
