import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tableFormat'
})
export class TableFormatPipe implements PipeTransform {

  transform(raw: number, col: string): string {
    switch (col) {
      case 'status':
        switch (raw) {
          case 1: return 'Pending';
          case 2: return 'Approved';
          case 3: return 'Denied';
        }
        break;
      case 'type':
        switch (raw) {
          case 1: return 'Lodging';
          case 2: return 'Travel';
          case 3: return 'Food';
          case 4: return 'Other';
        }
        break;
    }
    return 'Invalid Data';
  }
}
