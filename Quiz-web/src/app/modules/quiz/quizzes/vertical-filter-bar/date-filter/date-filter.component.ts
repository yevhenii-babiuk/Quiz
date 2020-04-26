import { Component, OnInit, Input, EventEmitter, Output, ViewEncapsulation } from '@angular/core';
import { DateFilter } from './date-filter.model';
import { FBFilterComponent } from '../fb-filter-component.interface';

@Component({
  selector: 'app-range-filter',
  templateUrl: './date-filter.component.html',
  styleUrls: ['./date-filter.component.css'],
})
export class DateFilterComponent implements OnInit, FBFilterComponent {

  @Input()
  public model: DateFilter

  @Output()
  change: EventEmitter<Date[]> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  rangeChanged(event: any): void {
    //let val=(<HTMLInputElement>document.getElementById('min')).value
    console.log(this.model.filterValue[0].toLocaleDateString());
    if(this.model.filterValue[0] === this.model.min && this.model.filterValue[1] === this.model.max)
      this.change.emit(undefined)
    else
      this.change.emit(this.model.filterValue)
  }
}
