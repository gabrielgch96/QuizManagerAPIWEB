import { Topic } from './topic';
import { Option } from './option';

export class Question{
    id: number;
    content: string;
    topic: Topic;
    options: Option[];

    constructor(text:string, topic: Topic ){
        this.content = text;
        this.topic = topic;
    }
}