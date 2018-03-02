import { Injectable } from '@angular/core';

@Injectable()
export class SessionService {

    static loggedIn:  boolean;

    constructor() {
      SessionService.loggedIn = false;
    }
}
