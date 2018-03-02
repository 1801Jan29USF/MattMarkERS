export class Reimbursement {
    id: number;
    amount: number;
    submitted: Date;
    resolved: Date;
    description: '';
    aName: '';
    rName: '';
    status: number;
    type: number;

    constructor() {
        this.id = 0;
        this.submitted = null;
        this.resolved = null;
        this.description = '';
        this.aName = '';
        this.rName = '';
        this.status = 0;
        this.type = 0;

    }
}
