import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { UIRouterModule } from '@uirouter/angular';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import {AppComponent} from './app.component';
import {NavComponent} from './nav/nav.component';

import { appRoutes } from './routes';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { EmployeeViewComponent } from './components/employee-view/employee-view.component';
import { ManagerViewComponent } from './components/manager-view/manager-view.component';
import { TableFormatPipe } from './pipes/table-format.pipe';

@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    NgbModule.forRoot(),
    RouterModule.forRoot(appRoutes),
    FormsModule
  ],
  declarations: [
    AppComponent,
    NavComponent,
    LoginComponent,
    EmployeeViewComponent,
    ManagerViewComponent,
    TableFormatPipe,
   ],
  providers: [

   ],
  bootstrap: [AppComponent]
})
export class AppModule { }
