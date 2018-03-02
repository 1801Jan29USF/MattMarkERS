import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { SessionService } from '../services/session.service';


@Injectable()
export class LoggedInGuard implements CanActivate {

  constructor( private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    if (SessionService.loggedIn) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
    }
  }
}
