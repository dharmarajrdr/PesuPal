import { useState } from 'react';
import './TicketDetailView.css'
import Loader from '../Loader';
import PageNotFound from '../Auth/PageNotFound';
import utils from '../../utils';
import ReactQuill from 'react-quill';

const TicketDescription = ({ html }) => <div id='ticket-description' className="html-content-renderer postContent" dangerouslySetInnerHTML={{ __html: html }} />

const SupportTicketComment = ({ comment }) => {

    const { id, createdAt, message, commentedBy } = comment || {};
    const { displayName, displayPicture } = commentedBy || {};
    return (
        <div className='support-ticket-comment w100 FCSB' key={id}>
            <div className='comment-header FRCS w100 mB10'>
                <img src={displayPicture} className='commented-by-display-picture img_20_20 mR10' />
                <span className='commented-by-name'>{displayName}</span>
                <span className='commented-by-date mL10 pL10'>{utils.convertDateAndTime(createdAt)}</span>
            </div>
            <div className='comment-message w100'>
                {message}
            </div>
        </div>
    );
};

const TicketDetailView = ({ ticket, ticketColor }) => {

    const [loader, setLoader] = useState(false);
    const [pageNotFound, setPageNotFound] = useState(false);
    const [ticketDetails, setTicketDetails] = useState(ticket || {
        ticketId: "TCK-1001",
        subject: "Login page error when entering special characters in username field",
        description: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis interdum metus vel lorem vestibulum tincidunt. Aliquam faucibus dui sed mi commodo volutpat. Vestibulum id magna vitae lacus commodo scelerisque. Phasellus mollis augue enim, <i>sed laoreet urna efficitur</i> sit amet. Pellentesque vitae enim eget purus tristique fringilla. Proin finibus ex orci, at porttitor nunc convallis at. Sed vitae eros vel elit vehicula blandit. Suspendisse et dictum risus, eget tincidunt quam. Proin tincidunt urna id purus rhoncus vulputate. Phasellus varius placerat eleifend. Curabitur sollicitudin commodo erat a euismod. Vestibulum elementum eget orci ut porta.
Nullam ex lorem, pellentesque vitae purus eget, <b>condimentum mattis nisl</b>.Curabitur volutpat scelerisque arcu at pharetra.Etiam pulvinar arcu id est accumsan, vitae ullamcorper urna faucibus.Sed consectetur purus enim, a ultrices eros varius a.Curabitur id ullamcorper urna. <b>In laoreet, justo ac dapibus interdum, turpis risus elementum leo</b>, quis lobortis dui mi in justo.Aliquam interdum sem at porttitor blandit.Vivamus placerat tincidunt ex sed sollicitudin.
Morbi massa ligula, ornare non ligula quis, faucibus tristique neque.In tellus ipsum, mollis et eros id, consequat elementum nisi. <br />Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Sed suscipit et lacus ac commodo.Nullam malesuada mi purus, gravida volutpat ex ultricies et.Duis ut ornare turpis.Pellentesque ultrices neque sit amet sem condimentum rhoncus vestibulum et nunc.Nullam pharetra, libero eu tincidunt consectetur, felis magna volutpat est, id tristique ipsum nisi quis nunc.Nullam a nibh at sem dapibus mattis.Fusce vel quam eu ligula pulvinar consequat.Fusce dignissim ornare sodales.In eu ex sit amet est commodo tempus.
Suspendisse blandit sem justo, id volutpat velit tincidunt sit amet.Curabitur dictum felis vestibulum interdum ultrices.Phasellus sit amet odio ut nibh ultrices dignissim.Duis non lorem enim.Nam volutpat, diam id commodo facilisis, leo ligula maximus mauris, eu commodo lorem ex vel lorem. <a href='#/'>Curabitur cursus dignissim</a> eros in semper.Curabitur a metus vel est placerat bibendum.Vestibulum ullamcorper orci ut eleifend vestibulum.Integer quis quam vehicula, posuere risus ut, accumsan turpis.Etiam augue ipsum, tincidunt ac convallis nec, lacinia quis mi.Mauris nisi ligula, iaculis id molestie quis, sollicitudin id elit.Nullam tortor dui, facilisis pulvinar ex eu, tempor sagittis massa.Quisque convallis ante vitae mi vulputate ullamcorper.Duis congue neque ut auctor tempus.Nullam cursus sed turpis et tempor.Mauris tristique aliquet mi, ac condimentum nisl tincidunt et.
Suspendisse rutrum porta ipsum, ac porttitor tortor cursus a.Pellentesque dictum augue vel dignissim pharetra.Duis gravida ligula leo.Curabitur eget sollicitudin diam. <br />Sed eu ipsum sit amet urna ultrices varius.Aliquam placerat vel justo ut tempor.Fusce consequat neque orci, eget suscipit neque bibendum faucibus.Nulla facilisi.Interdum et malesuada fames ac ante ipsum primis in faucibus.Etiam vel sapien lectus.Mauris a egestas nulla, sit amet molestie velit.Mauris nec metus a libero commodo sollicitudin at ut nisi.`,
        status: "Open",
        createdAt: "2025-08-07T09:15:32Z",
        attachments: [
            "https://cdni.iconscout.com/illustration/premium/thumb/female-customer-support-executive-6210200-5106357.png",
            "https://cdn-icons-png.flaticon.com/512/6797/6797164.png",
            "https://support.microsoft.com/images/en-us/99711ca1-7c4f-42d5-a3e9-c1fc97afb881",
            "https://png.pngtree.com/png-clipart/20220320/original/pngtree-cosmic-star-astronaut-paper-cut-wind-illustration-png-image_7463250.png",
            "https://www.k12.com/wp-content/uploads/2025/07/K12-HP-Hero-Large-Desktop.png",
            "https://cdn-ildoopd.nitrocdn.com/wkkYgkWhAeZimJYFEiyEHubOeiHSRmaN/assets/images/optimized/rev-72c8e8c/www.k12.com/wp-content/uploads/2024/09/StatePage-Indiana-img-2-1.png"
        ]
    });

    const { ticketId, subject, description, status, createdAt, attachments } = ticketDetails || {};

    const [comments, setComments] = useState([
        {
            id: "CMT-1005",
            createdAt: "2025-08-08T17:12:03Z",
            message: "I have reviewed the issue and it looks like the API is returning the wrong data type.",
            commentedBy: {
                displayName: "Zoe Carter",
                displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
                id: "USR-105"
            }
        },
        {
            id: "CMT-1002",
            createdAt: "2025-08-07T09:45:10Z",
            message: "We should probably run unit tests on this module before moving forward.",
            commentedBy: {
                displayName: "William Smith",
                displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
                id: "USR-102"
            }
        },
        {
            id: "CMT-1004",
            createdAt: "2025-08-08T14:20:55Z",
            message: "The UI fix has been deployed to staging for verification.",
            commentedBy: {
                displayName: "Victoria Lee",
                displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
                id: "USR-104"
            }
        },
        {
            id: "CMT-1003",
            createdAt: "2025-08-07T16:33:22Z",
            message: "I've pushed a hotfix branch, please test and confirm.",
            commentedBy: {
                displayName: "PesuPal Support",
                displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
                id: "USR-103"
            }
        },
        {
            id: "CMT-1001",
            createdAt: "2025-08-06T12:15:48Z",
            message: "Initial comment for context on the bug report.",
            commentedBy: {
                displayName: "Sophia Johnson",
                displayPicture: "https://www.updatenews360.com/english/wp-content/uploads/2022/04/Xefntr7z_400x400.jpg",
                id: "USR-101"
            }
        }
    ]);


    return <div id='ticket-detail-view' className='FCSS w100 h100'>
        {
            loader ? <Loader /> :
                pageNotFound ? <PageNotFound /> : (
                    <div id='ticket-detail-view' className='w100 FCSB'>
                        <div id='ticket-detail-header' className='w100 FRSB'>
                            <div id='ticket-subject-id' className='FCSS'>
                                <h3>{subject}</h3>
                                <span id='ticket-id'>#{ticketId}</span>
                            </div>
                            <span id='ticket-status' style={{ backgroundColor: ticketColor?.[status] }}>{status}</span>
                        </div>
                        <TicketDescription html={description} />
                        <div id='ticket-attachments' className='FRSS w100 mT10'>
                            {attachments?.map((attachment, index) => (
                                <div className='attachment'>
                                    <img key={index} src={attachment} alt={`Ticket Attachment ${index + 1}`} />
                                </div>
                            ))}
                        </div>
                        <span id='ticket-created-at'>Created on {utils.convertDateAndTime(createdAt)}</span>
                        <div id='ticket-comments' className='FCSB mT20'>
                            <h3>Comments({comments.length})</h3>
                            <ReactQuill theme="snow" className='w100' id='add-comment' placeholder='Add a comment...' />
                            {comments.map(comment => (
                                <SupportTicketComment key={comment.id} comment={comment} />
                            ))}
                        </div>
                    </div>
                )
        }
    </div>
}

export default TicketDetailView