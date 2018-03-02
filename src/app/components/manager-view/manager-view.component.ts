import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../../beans/reimbursement';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { LoggedInGuard } from '../../guards/logged-in-guard.guard';
import { SessionService} from '../../services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-view',
  templateUrl: './manager-view.component.html',
  styleUrls: ['./manager-view.component.css']
})
export class ManagerViewComponent implements OnInit {

  rList: Array<Reimbursement> = [];
  constructor(private client: HttpClient,  private router: Router) { }

  ngOnInit() {
    if (!SessionService.manager) {
      this.router.navigateByUrl('/login');
    }
    this.client.get(`${environment.context}manager`,
      {withCredentials: true})
      .subscribe(
        (succ: Array<Reimbursement>) => {
          this.rList = succ;
        },
        (err) => {
          console.log('failed to load reimbursements');
        }
      );
  }

  approve(toApprove: Reimbursement) {
    toApprove.status = 2;
    this.client.post(`${environment.context}manager`, toApprove,
    {withCredentials: true})
      .subscribe(
        (succ: any) => {
        },
        (err) => {
        }
      );
  }

  deny(toDeny: Reimbursement) {
    toDeny.status = 3;
    this.client.post(`${environment.context}manager`, toDeny,
    {withCredentials: true})
      .subscribe(
        (succ: any) => {
        },
        (err) => {
        }
      );
  }


}
