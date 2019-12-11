import { User } from './user'
import { Question } from './question';
import { Exam } from './exam';
import { Option } from './option';

export class Answer{

    id: number;
    user: User;
    question: Question;
    //exam: Exam;
    selectedOptions: Option[];

    constructor(){
        this.selectedOptions = [];
    }

}