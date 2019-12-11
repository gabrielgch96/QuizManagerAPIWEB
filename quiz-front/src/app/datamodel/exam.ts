import { Question } from './question';

export class Exam{
    id: number;
    title: string;
    questions: Question[];

    constructor(title: string ){
        this.title = title;
    }
}