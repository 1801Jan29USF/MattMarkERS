import { Injectable } from '@angular/core';

@Injectable()
export class SessionService {

    static employee:  boolean;
    static manager:  boolean;

    constructor() {
      SessionService.employee = false;
      SessionService.manager = false;
    }
}
