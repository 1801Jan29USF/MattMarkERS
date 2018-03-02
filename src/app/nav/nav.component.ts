import { Component } from '@angular/core';
import { SessionService} from '../services/session.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {
  logout() {
    SessionService.employee = false;
    SessionService.manager = false;
  }
}
