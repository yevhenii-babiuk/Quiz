import { FBFilter } from "../fb-filter.interface";

export class DateFilter implements FBFilter {

    public filterValue: Date[] = []

    constructor(
        public identifier: string,
        public title: string,
        public min: Date,
        public max: Date,
    ) {
        this.filterValue.push(min)
        this.filterValue.push(max)
    }

    public getClassName(): string {
        return 'DateFilter'
    }

    public resetFilterValue(): void {
        this.filterValue = []
        this.filterValue.push(this.min)
        this.filterValue.push(this.max)
    }
}
