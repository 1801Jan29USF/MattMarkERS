import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { EmployeeViewComponent } from './components/employee-view/employee-view.component';
import { ManagerViewComponent } from './components/manager-view/manager-view.component';

export const appRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'employee',
    component: EmployeeViewComponent
  },
  {
    path: 'manager',
    component: ManagerViewComponent
  },
  {
    path: '**',
    component: LoginComponent
  }
];
