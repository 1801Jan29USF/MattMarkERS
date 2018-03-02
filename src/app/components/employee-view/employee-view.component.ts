import { Component, OnInit } from '@angular/core';
import { Reimbursement } from '../../beans/reimbursement';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { SessionService} from '../../services/session.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-employee-view',
  templateUrl: './employee-view.component.html',
  styleUrls: ['./employee-view.component.css']
})
export class EmployeeViewComponent implements OnInit {

  typeInput: string;
  reimbursement: Reimbursement = new Reimbursement();
  rList: Array<Reimbursement> = [];

  constructor(private client: HttpClient,  private router: Router) { }

  ngOnInit() {
    if (!SessionService.manager && !SessionService.employee) {
      this.router.navigateByUrl('/login');
    }
    this.client.get(`${environment.context}employee`,
      { withCredentials: true })
      .subscribe(
        (succ: Array<Reimbursement>) => {
          this.rList = succ;
          console.log(this.rList);
        },
        (err) => {
          console.log('failed to load reimbursements');
        }
      );
  }

  rSubmit() {
    switch (this.typeInput) {
      case 'lodging':
        this.reimbursement.type = 1;
        break;
      case 'travel':
        this.reimbursement.type = 2;
        break;
      case 'food':
        this.reimbursement.type = 3;
        break;
      default:
        this.reimbursement.type = 4;
        break;
    }
    this.reimbursement.status = 1;
    this.rList.unshift(this.reimbursement);
    this.client.post(`${environment.context}employee`, this.reimbursement,
      { withCredentials: true })
      .subscribe(
        (succ: any) => {
        },
        (err) => {
        }
      );
  }

}
